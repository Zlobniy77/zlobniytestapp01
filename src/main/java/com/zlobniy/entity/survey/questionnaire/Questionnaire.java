package com.zlobniy.entity.survey.questionnaire;

import java.util.ArrayList;
import java.util.List;

public class Questionnaire {

    private List<Question> questions = new ArrayList<>(  );

    public List<Question> getQuestions(){
        return questions;
    }

    public void setQuestions( List<Question> questions ){
        this.questions = questions;
    }
}
