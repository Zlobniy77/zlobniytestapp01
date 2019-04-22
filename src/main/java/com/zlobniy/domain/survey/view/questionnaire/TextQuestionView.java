package com.zlobniy.domain.survey.view.questionnaire;

import com.zlobniy.domain.survey.entity.questionnaire.TextQuestion;

public class TextQuestionView extends QuestionView {

    private static final String TYPE = "text";

    private OptionView optionView = new OptionView(  );

    public TextQuestionView(){

    }

    public TextQuestionView( TextQuestion textQuestion ){
        super( textQuestion, TYPE );
        this.optionView = new OptionView( textQuestion.getOption() );
    }

    public OptionView getOptionView() {
        return optionView;
    }

    public void setOptionView( OptionView optionView ) {
        this.optionView = optionView;
    }

    public String getType(){
        return TYPE;
    }

}
