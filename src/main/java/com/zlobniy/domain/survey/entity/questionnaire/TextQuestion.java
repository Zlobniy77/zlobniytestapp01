package com.zlobniy.domain.survey.entity.questionnaire;

import com.zlobniy.domain.survey.view.questionnaire.TextQuestionView;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
public class TextQuestion extends Question {

    @OneToOne( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private Option option;

    public TextQuestion(){

    }

    public TextQuestion( TextQuestionView questionView ){
        super( questionView );
        this.option = new Option( questionView.getOptionView() );
    }

    public Option getOption() {
        return option;
    }

    public void setOption( Option option ) {
        this.option = option;
    }
}
