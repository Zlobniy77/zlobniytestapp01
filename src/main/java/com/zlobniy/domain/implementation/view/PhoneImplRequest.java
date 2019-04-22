package com.zlobniy.domain.implementation.view;

public class PhoneImplRequest {

    private Long surveyId;
    private Long panelId;
    private Long clientId;

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId( Long surveyId ) {
        this.surveyId = surveyId;
    }

    public Long getPanelId() {
        return panelId;
    }

    public void setPanelId( Long panelId ) {
        this.panelId = panelId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId( Long clientId ) {
        this.clientId = clientId;
    }
}
