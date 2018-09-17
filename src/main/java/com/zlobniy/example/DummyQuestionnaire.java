package com.zlobniy.example;

import com.zlobniy.domain.survey.entity.Survey;
import com.zlobniy.domain.survey.view.SurveySettingsView;
import com.zlobniy.domain.survey.view.SurveyView;
import com.zlobniy.domain.survey.view.questionnaire.*;

import java.util.Date;

public class DummyQuestionnaire {

    public QuestionnaireView createDummyQuestionnaire(){
        QuestionnaireView questionnaireView = new QuestionnaireView();
        questionnaireView.getQuestions().add( createDummyClosedQuestion( 1L ) );
//        questionnaireView.getQuestions().add( createDummyClosedQuestion( 2L ) );
        questionnaireView.getQuestions().add( createDummyMatrixQuestion( 2L ) );

        return questionnaireView;
    }


    public QuestionView createDummyClosedQuestion(Long id ){
        ClosedQuestionView question = new ClosedQuestionView();
//        question.setId( id );
        question.setSettings( createDummySettings() );
        question.setTitle( "dummy closed question " + id );

        question.getOptions().add( createDummyOption( 1L ) );
        question.getOptions().add( createDummyOption( 2L ) );
        question.getOptions().add( createDummyOption( 3L ) );

        return question;
    }

    public QuestionView createDummyMatrixQuestion(Long id ){
        MatrixQuestionView question = new MatrixQuestionView();
//        question.setId( id );
        question.setSettings( createDummySettings() );
        question.setTitle( "Dummy matrix question " + id );

        question.getOptions().add( createDummyOption( 1L ) );
        question.getOptions().add( createDummyOption( 2L ) );
        question.getOptions().add( createDummyOption( 3L ) );

        question.getScales().add( createDummyScale( 1L ) );

        return question;
    }

    public OptionView createDummyOption(Long id ){
        OptionView optionView = new OptionView();
//        optionView.setId( id );
        optionView.setTitle( "Dummy option " + id );

        return optionView;
    }

    public ScaleView createDummyScale(Long id ){
        ScaleView scaleView = new ScaleView();
//        scaleView.setId( id );
        scaleView.setTitle( "Dummy scale " + id );

        scaleView.getOptions().add( createDummyScaleStep( 1L ) );
        scaleView.getOptions().add( createDummyScaleStep( 2L ) );
        scaleView.getOptions().add( createDummyScaleStep( 3L ) );

        return scaleView;
    }

    public OptionView createDummyScaleStep(Long id ){
        OptionView scaleStep = new OptionView();
//        scaleStep.setId( id );
        scaleStep.setTitle( "Dummy scale step " + id );

        return scaleStep;
    }

    public QuestionSettingsView createDummySettings(){
        QuestionSettingsView questionSettingsView = new QuestionSettingsView();
        questionSettingsView.setLayout( "radio" );


        return questionSettingsView;
    }

    public static void main(String[] args) {

        DummyQuestionnaire dummyQuestionnaire = new DummyQuestionnaire();

        SurveyView surveyView = new SurveyView();
        surveyView.setId( 1L );
        surveyView.setCreationDate( new Date() );
        surveyView.setQuestionnaire( dummyQuestionnaire.createDummyQuestionnaire() );
        surveyView.setSurveySettings( new SurveySettingsView() );
        surveyView.setTitle( "Test title" );

        Survey survey = new Survey( surveyView );

        System.out.println( survey );

    }

}
