package com.zlobniy.domain.survey.entity.questionnaire;

import com.zlobniy.domain.survey.view.questionnaire.TextQuestionView;

import javax.persistence.Entity;

@Entity
public class TextQuestion extends Question {

    public TextQuestion(){

    }

    public TextQuestion( TextQuestionView questionView ){
        super( questionView );
    }

}
