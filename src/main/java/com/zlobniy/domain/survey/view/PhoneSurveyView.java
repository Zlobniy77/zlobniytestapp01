package com.zlobniy.domain.survey.view;

import com.zlobniy.domain.export.ExportAnswerView;
import com.zlobniy.domain.survey.entity.Survey;

import java.util.ArrayList;
import java.util.List;

public class PhoneSurveyView extends SurveyView {

    private boolean done = false;
    private int index = 0;
    private boolean started = false;
    private String phone;
    private List<ExportAnswerView> responses = new ArrayList<>();

    public PhoneSurveyView(){
        super();
    }

    public PhoneSurveyView( Survey survey ){
        super( survey );
    }

    public boolean isDone() {
        if ( index > this.getQuestionnaire().getQuestions().size() - 1 ) {
            this.markDone();
        }
        return done;
    }

    private void markDone(){
        this.done = true;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void addResponse( ExportAnswerView response ){
        this.index++;

        this.responses.add( response );
    }

    public List<ExportAnswerView> getResponses() {
        return responses;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
