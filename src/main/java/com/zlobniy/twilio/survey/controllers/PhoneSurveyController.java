package com.zlobniy.twilio.survey.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zlobniy.domain.answer.entity.AnswerSession;
import com.zlobniy.domain.answer.view.AnswerView;
import com.zlobniy.domain.export.ExportAnswerView;
import com.zlobniy.domain.survey.view.PhoneSurveyView;
import com.zlobniy.twilio.survey.models.Survey;
import com.zlobniy.twilio.survey.models.SurveyService;
import com.zlobniy.twilio.survey.util.IncomingCall;
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
