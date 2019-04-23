package com.zlobniy.domain.implementation.phone;

import com.twilio.sdk.verbs.*;
import com.zlobniy.domain.answer.entity.Answer;
import com.zlobniy.domain.answer.entity.AnswerSession;
import com.zlobniy.domain.answer.entity.Element;
import com.zlobniy.domain.answer.service.AnswerService;
import com.zlobniy.domain.answer.view.AnswerOptionView;
import com.zlobniy.domain.answer.view.AnswerView;
import com.zlobniy.domain.export.ExportAnswerView;
import com.zlobniy.domain.implementation.view.IncomingCall;
import com.zlobniy.domain.implementation.view.Response;
import com.zlobniy.domain.survey.view.PhoneSurveyView;
import com.zlobniy.domain.survey.view.questionnaire.ClosedQuestionView;
import com.zlobniy.domain.survey.view.questionnaire.QuestionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PhoneImplService {

    private final Map<Long, PhoneSurveyView> surveys = new HashMap<>();

    private AnswerService answerService;

    @Autowired
    public PhoneImplService( AnswerService answerService ){
        this.answerService = answerService;
    }

    public void addSurvey( Long id, PhoneSurveyView survey ){
        surveys.putIfAbsent( id , survey );
    }

    /**
     * Add transcribed answer, speech to text.
     * */
    public void addAnswerData( Long surveyId, int questionNumber, String userId, String value, String extraInfo ){

        answerService.updateAnswersElements( surveyId, userId, questionNumber, value, extraInfo );
    }

    public List<ExportAnswerView> getAnswers( Long surveyId ){
        System.out.println("Get results for survey " + surveyId);

        List<AnswerSession> sessions = answerService.getSesions( surveyId );
        List<ExportAnswerView> exportData = new ArrayList<>();

        for (AnswerSession session : sessions) {

            for (Answer answer : session.getAnswers()) {

                for (Element element : answer.getElements()) {
                    ExportAnswerView export = new ExportAnswerView();

                    export.setRespondent( session.getUserId() );
                    export.setQuestionNumber( answer.getQuestionNumber() );
                    if( element.getAnswerOrder() != null ){
                        export.setValue( element.getAnswerOrder().toString() );
                    }else{
                        String value = element.getValue() + " ( " + element.getExtraInfo() + " ) ";
                        export.setValue( value );
                    }

                    exportData.add( export );
                }

            }

        }

        return exportData;
    }

    public void resetSurvey( Long surveyId ){
        surveys.remove( surveyId );
    }

    public String phoneHandler( IncomingCall call, Long id )
            throws TwiMLException, UnsupportedEncodingException {

        TwiMLResponse twiml = new TwiMLResponse();

        PhoneSurveyView survey = surveys.get( id );

        if( !survey.isDone() ){
            if( !survey.isStarted() ){
                survey.setStarted( true );
            }else{
                AnswerView answerView = processAnswer( call, survey );
                answerService.addAnswer( answerView );
            }
            if( !survey.isDone() ){
                return processSurvey( survey, twiml );
            }

        }

        twiml.append(new Say("Your responses have been recorded. Thank you for your time!"));

        survey.setIndex( 0 );
        survey.setDone( false );
        survey.setStarted( false );

        return twiml.toXML();

    }

    AnswerView processAnswer( IncomingCall call, PhoneSurveyView survey ){
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

            saveClosedAnswer( response1, q, exportAnswerView, answerView );

        }else if( q.getType().equalsIgnoreCase( "text" ) ){

            exportAnswerView.setUrl( response1.getAnswer().toString() );

            AnswerOptionView answerOptionView = new AnswerOptionView(  );
            answerOptionView.setName( "url" );
            answerOptionView.setValue( response1.getAnswer().toString() );

            answerView.getOptions().add( answerOptionView );

        }

        survey.addResponse( exportAnswerView );

        return answerView;
    }

    private void saveClosedAnswer( Response response1, QuestionView q, ExportAnswerView exportAnswerView, AnswerView answerView ){
        List<AnswerOptionView> options = new ArrayList<>();
        Integer selectedIndex = Integer.parseInt( response1.getAnswer().toString() );

        ClosedQuestionView closedQuestion = (ClosedQuestionView)q;

        int index = 1;
        for ( com.zlobniy.domain.survey.view.questionnaire.OptionView opt : closedQuestion.getOptions() ) {

            boolean selected = selectedIndex == index;
            if( selected ){
                exportAnswerView.setValue( opt.getTitle() );
            }

            AnswerOptionView option = new AnswerOptionView();
            option.setIndex( index );
            option.setSelected( selected );
            option.setValue( "" );
            option.setName( "option_" + index );
            option.setScaleGroup( new ArrayList<>() );

            options.add( option );

            index++;
        }

        answerView.setOptions( options );
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
            case "text":
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
        text.setTranscribeCallback("/interview/" + survey.getId() + "/transcribe/" + ( survey.getIndex() + 1 ) + "/test");
        twiml.append(text);

    }

}
