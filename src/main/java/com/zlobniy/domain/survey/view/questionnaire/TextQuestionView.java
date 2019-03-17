package com.zlobniy.domain.survey.view.questionnaire;

import com.zlobniy.domain.survey.entity.questionnaire.TextQuestion;

public class TextQuestionView extends QuestionView {

    private static final String TYPE = "text";

    public TextQuestionView(){

    }

    public TextQuestionView( TextQuestion textQuestion ){
        super( textQuestion, TYPE );
    }


    public String getType(){
        return TYPE;
    }

}
