package com.zlobniy.example;

import com.zlobniy.domain.survey.entity.Survey;
import com.zlobniy.domain.survey.view.SurveySettingsView;
import com.zlobniy.domain.survey.view.SurveyView;

public class DummySurvey {

    public Survey createSurveyWithOpenQuestions(){

        DummyQuestionnaire dummyQuestionnaire = new DummyQuestionnaire();

        SurveyView surveyView = new SurveyView(  );
        surveyView.setSurveySettings( new SurveySettingsView(  ) );
        surveyView.setQuestionnaire( dummyQuestionnaire.createQuestionnaireWithOpenQuestions() );

        return new Survey( surveyView );
    }

}
