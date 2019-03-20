package com.zlobniy.twilio.survey.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twilio.sdk.verbs.*;
import com.zlobniy.BoosterApplication;
import com.zlobniy.domain.answer.entity.AnswerSession;
import com.zlobniy.domain.answer.view.AnswerView;
import com.zlobniy.domain.answer.view.OptionView;
import com.zlobniy.domain.export.ExportAnswerView;
import com.zlobniy.domain.survey.view.PhoneSurveyView;
import com.zlobniy.domain.survey.view.questionnaire.ClosedQuestionView;
import com.zlobniy.domain.survey.view.questionnaire.QuestionView;
import com.zlobniy.twilio.survey.models.Response;
import com.zlobniy.twilio.survey.models.Survey;
import com.zlobniy.twilio.survey.models.SurveyService;
import com.zlobniy.twilio.survey.util.IncomingCall;
import com.zlobniy.twilio.survey.util.Question;
import org.springframework.web.bind.annotation.RestController;
import spark.Route;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PhoneSurveyController {

  private static final Map<String, PhoneSurveyView> surveysMap = new HashMap<>();
  private static final List<AnswerView> answersViewList = new ArrayList<>();
  private static final List<AnswerSession> answerSessionsList = new ArrayList<>();

  private static SurveyService surveys = new SurveyService();

  // Main interview loop.
  public Route interview = ( request, response) -> {

    IncomingCall call = new IncomingCall( parseBody(request.body()) );
    TwiMLResponse twiml = new TwiMLResponse();

    PhoneSurveyView survey = getRunnedSurvey( call.getFrom() );

    if( !survey.isDone() ){
      if( !survey.isStarted() ){
        survey.setStarted( true );
      }else{

        Response response1 = new Response( call.getInput() );

        ExportAnswerView exportAnswerView = new ExportAnswerView();
        exportAnswerView.setQuestionNumber( survey.getIndex() + 1 );

        exportAnswerView.setRespondent( "test" );

        int questionNumber = survey.getIndex();
        QuestionView q = survey.getQuestionnaire().getQuestions().get( survey.getIndex() );

        AnswerView answerView = new AnswerView();
        answerView.setQuestionType( q.getType() );
        answerView.setQuestionId( q.getId() );
        answerView.setQuestionNumber( questionNumber + 1 );
        answerView.setSurveyId( survey.getId() );
        answerView.setUserId( "test" );

        List<OptionView> options = new ArrayList<>();

        if( q.getType().equalsIgnoreCase( "closed" ) ){

          Integer selectedIndex = Integer.parseInt( response1.getAnswer().toString() );

          ClosedQuestionView closedQuestion = (ClosedQuestionView)q;

          int index = 1;
          for ( com.zlobniy.domain.survey.view.questionnaire.OptionView opt : closedQuestion.getOptions() ) {

            boolean selected = selectedIndex == index;
            if( selected ){
              exportAnswerView.setValue( opt.getTitle() );
            }

            OptionView option = new OptionView();
            option.setIndex( index );
            option.setSelected( selected );
            option.setValue( "" );
            option.setName( "option_" + index );
            option.setScaleGroup( new ArrayList<>() );

            options.add( option );

            index++;
          }

          answerView.setOptions( options );
          answerView.setFreeTextOption( new OptionView() );

        }else if( q.getType().equalsIgnoreCase( "text" ) ){

          exportAnswerView.setValue( response1.getAnswer().toString() );

        }

        AnswerSession answerSession = new AnswerSession( answerView );

        answersViewList.add( answerView );
        answerSessionsList.add( answerSession );

        survey.addResponse( exportAnswerView );
      }
      if( !survey.isDone() ){
        newContinueSurvey( survey, twiml );
      }

    }

    twiml.append(new Say("Your responses have been recorded. Thank you for your time!"));
    return twiml.toXML();
  };

  public static List<ExportAnswerView> getResults( Long id ){
    System.out.println("Get results for survey " + id);
    List<ExportAnswerView> answers = new ArrayList<>();
    for (PhoneSurveyView survey : surveysMap.values()) {

      answers.addAll( survey.getResponses() );
    }
    return answers;
  }

  // Results accessor route
  public Route results = ( request, response) -> {

    ObjectMapper mapper = new ObjectMapper(  );

    List<ExportAnswerView> answers = new ArrayList<>();
    for (PhoneSurveyView survey : surveysMap.values()) {
      answers.addAll( survey.getResponses() );
    }

    String json = mapper.writeValueAsString( answers );
    response.type("application/json");
    return json;
    };

  // Transcription route (called by Twilio's callback, once transcription is complete)
  public Route transcribe = ( request, response) -> {
    IncomingCall call = new IncomingCall(parseBody(request.body()));
      // Get the phone and question numbers from the URL parameters provided by the "Record" verb
      String surveyId = request.params(":phone");
      int questionId = Integer.parseInt(request.params(":question"));
      // Find the survey in the DB...
      Survey survey = surveys.getSurvey(surveyId);
      // ...and update it with our transcription text.
      survey.getResponses()[questionId].setAnswer(call.getTranscriptionText());
      surveys.updateSurvey(survey);
      response.status(200);
      return "OK";
    };

  protected static String newContinueSurvey( PhoneSurveyView survey, TwiMLResponse twiml ) throws TwiMLException,
          UnsupportedEncodingException {

    QuestionView question = survey.getQuestionnaire().getQuestions().get( survey.getIndex() );

    Say say = new Say( question.getTitle() );
    twiml.append(say);
    // Depending on the question type, create different TwiML verbs.
    switch ( question.getType() ) {
      case "closed":
        closedQuestionGather( question, twiml );
        break;
      case "freeText":
        freeTextGather( twiml, survey );
        break;
      case "number":
        Say numInstructions = new Say("Enter the number on your keypad, followed by the #.");
        twiml.append(numInstructions);
        Gather numberGather = new Gather();
        // Listen until a user presses "#"
        numberGather.setFinishOnKey("#");
        twiml.append(numberGather);
        break;
    }
    return twiml.toXML();
  }

  private static void closedQuestionGather( QuestionView question, TwiMLResponse twiml ) throws TwiMLException {

    StringBuilder instruction = new StringBuilder();

    ClosedQuestionView closedQuestion = (ClosedQuestionView)question;

    int index = 1;
    for ( com.zlobniy.domain.survey.view.questionnaire.OptionView option : closedQuestion.getOptions() ) {
      instruction.append( "press "  )
              .append( index )
              .append( " if your choice " )
              .append( option.getTitle() ).append(". ");
      index++;
    }

    instruction.append( "Enter the number on your keypad, followed by the #." );

    Say textInstructions = new Say( instruction.toString() );
    twiml.append(textInstructions);

    Gather gather = new Gather();
    gather.setFinishOnKey("#");

    twiml.append(gather);

  }

  private static void freeTextGather( TwiMLResponse twiml,
                                      PhoneSurveyView survey ) throws TwiMLException, UnsupportedEncodingException {

    Say textInstructions =
            new Say(
                    "Your response will be recorded after the tone. Once you have finished recording, press the #.");
    twiml.append(textInstructions);
    Record text = new Record();
    text.setFinishOnKey("#");
    // Use the Transcription route to receive the text of a voice response.
    text.setTranscribe(true);
    text.setTranscribeCallback("/interview/" + urlEncode( survey.getPhone() ) + "/transcribe/" + survey.getIndex());
    twiml.append(text);

  }

  // Helper methods
  protected static String continueSurvey(Survey survey, TwiMLResponse twiml) throws TwiMLException,
      UnsupportedEncodingException {
    Question question = BoosterApplication.config.getQuestions()[survey.getIndex()];
    Say say = new Say(question.getText());
    twiml.append(say);
    // Depending on the question type, create different TwiML verbs.
    switch (question.getType()) {
      case "text":
        Say textInstructions =
            new Say(
                "Your response will be recorded after the tone. Once you have finished recording, press the #.");
        twiml.append(textInstructions);
        Record text = new Record();
        text.setFinishOnKey("#");
        // Use the Transcription route to receive the text of a voice response.
        text.setTranscribe(true);
        text.setTranscribeCallback("/interview/" + urlEncode(survey.getPhone()) + "/transcribe/"
            + survey.getIndex());
        twiml.append(text);
        break;
      case "boolean":
        Say boolInstructions =
            new Say("Press 0 to respond 'No,' and press any other number to respond 'Yes.'");
        twiml.append(boolInstructions);
        Gather booleanGather = new Gather();
        // Listen only for one digit.
        booleanGather.setNumDigits(1);

        twiml.append(booleanGather);
        break;
      case "number":
        Say numInstructions = new Say("Enter the number on your keypad, followed by the #.");
        twiml.append(numInstructions);
        Gather numberGather = new Gather();
        // Listen until a user presses "#"
        numberGather.setFinishOnKey("#");
        twiml.append(numberGather);
        break;
    }
    return twiml.toXML();
  }

  // Spark has no built-in body parser, so let's roll our own.
  public static Map<String, String> parseBody(String body) throws UnsupportedEncodingException {
    String[] unparsedParams = body.split("&");
    Map<String, String> parsedParams = new HashMap<String, String>();
    for (int i = 0; i < unparsedParams.length; i++) {
      String[] param = unparsedParams[i].split("=");
      if (param.length == 2) {
        parsedParams.put(urlDecode(param[0]), urlDecode(param[1]));
      } else if (param.length == 1) {
        parsedParams.put(urlDecode(param[0]), "");
      }
    }
    return parsedParams;
  }

  // Wrap the URLEncoder and URLDecoder for cleanliness.
  public static String urlEncode(String s) throws UnsupportedEncodingException {
    return URLEncoder.encode(s, "utf-8");
  }

  public static String urlDecode(String s) throws UnsupportedEncodingException {
    return URLDecoder.decode(s, "utf-8");
  }

  public static PhoneSurveyView getRunnedSurvey( String phoneNumber ){
    return surveysMap.get( phoneNumber );
  }

  public static void addToRunner( String phoneNumber, com.zlobniy.domain.survey.entity.Survey survey ){
    PhoneSurveyView surveyView = new PhoneSurveyView( survey );
    surveyView.setPhone( phoneNumber );
    surveysMap.put( phoneNumber, surveyView );
  }

  public static Map<String, PhoneSurveyView> getSurveysMap() {
    return surveysMap;
  }

  public static List<AnswerView> getAnswersViewList() {
    return answersViewList;
  }

  public static List<AnswerSession> getAnswerSessionsList() {
    return answerSessionsList;
  }
}
