package com.zlobniy.domain.implementation;

import com.twilio.sdk.verbs.*;
import com.zlobniy.domain.answer.entity.AnswerSession;
import com.zlobniy.domain.answer.view.AnswerView;
import com.zlobniy.domain.answer.view.OptionView;
import com.zlobniy.domain.export.ExportAnswerView;
import com.zlobniy.domain.survey.view.PhoneSurveyView;
import com.zlobniy.domain.survey.view.questionnaire.ClosedQuestionView;
import com.zlobniy.domain.survey.view.questionnaire.QuestionView;
import com.zlobniy.twilio.survey.models.Response;
import com.zlobniy.twilio.survey.util.IncomingCall;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Component
public class PhoneImplService {


    public String phoneHandler( IncomingCall call, PhoneSurveyView survey )
            throws TwiMLException, UnsupportedEncodingException {

        TwiMLResponse twiml = new TwiMLResponse();

        if( !survey.isDone() ){
            if( !survey.isStarted() ){
                survey.setStarted( true );
            }else{
                saveAnswers( call, survey );

            }
            if( !survey.isDone() ){
                return processSurvey( survey, twiml );
            }

        }

        twiml.append(new Say("Your responses have been recorded. Thank you for your time!"));
        return twiml.toXML();

    }

    private void saveAnswers( IncomingCall call, PhoneSurveyView survey ){
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

        if( q.getType().equalsIgnoreCase( "closed" ) ){

            saveClosedAnswer( response1, q, exportAnswerView );

        }else if( q.getType().equalsIgnoreCase( "text" ) ){

            exportAnswerView.setValue( response1.getAnswer().toString() );

        }

        AnswerSession answerSession = new AnswerSession( answerView );

//                answersViewList.add( answerView );
//                answerSessionsList.add( answerSession );

        survey.addResponse( exportAnswerView );
    }

    private void saveClosedAnswer( Response response1, QuestionView q, ExportAnswerView exportAnswerView ){
        List<OptionView> options = new ArrayList<>();
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

        //answerView.setOptions( options );
        //answerView.setFreeTextOption( new OptionView() );
    }

    private String processSurvey( PhoneSurveyView survey, TwiMLResponse twiml ) throws TwiMLException,
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

        Say textInstructions = new Say("Your response will be recorded after the tone. " +
                                "Once you have finished recording, press the #.");
        twiml.append(textInstructions);
        Record text = new Record();
        text.setFinishOnKey("#");
        // Use the Transcription route to receive the text of a voice response.
        text.setTranscribe(true);
        text.setTranscribeCallback("/interview/" + urlEncode( survey.getPhone() ) + "/transcribe/" + survey.getIndex());
        twiml.append(text);

    }


    // Wrap the URLEncoder and URLDecoder for cleanliness.
    public static String urlEncode(String s) throws UnsupportedEncodingException {
        return URLEncoder.encode(s, "utf-8");
    }

    public static String urlDecode(String s) throws UnsupportedEncodingException {
        return URLDecoder.decode(s, "utf-8");
    }

}
