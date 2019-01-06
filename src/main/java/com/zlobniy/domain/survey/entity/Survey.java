package com.zlobniy.domain.survey.entity;

import com.zlobniy.domain.client.entity.Client;
import com.zlobniy.domain.folder.entity.Folder;
import com.zlobniy.domain.survey.entity.questionnaire.Questionnaire;
import com.zlobniy.domain.survey.view.SurveyView;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Survey {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String title;

    @OneToOne( cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    private Questionnaire questionnaire;

    @OneToOne( cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    private SurveySettings surveySettings;

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    @JoinColumn( name = "folder_id" )
    private Folder folder;

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    @JoinColumn( name = "client_id" )
    @NotNull
    private Client client;

    @Column
    private Date creationDate;

    public Survey() {

    }

    public Survey( SurveyView surveyView ) {
        setId( surveyView.getId() );
        setCreationDate( surveyView.getCreationDate() );
        setTitle( surveyView.getTitle() );
        setQuestionnaire( new Questionnaire( surveyView.getQuestionnaire() ) );
        setSurveySettings( new SurveySettings( surveyView.getSurveySettings() ) );
    }

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire( Questionnaire questionnaire ) {
        this.questionnaire = questionnaire;
    }

    public SurveySettings getSurveySettings() {
        return surveySettings;
    }

    public void setSurveySettings( SurveySettings surveySettings ) {
        this.surveySettings = surveySettings;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate( Date creationDate ) {
        this.creationDate = creationDate;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder( Folder folder ) {
        this.folder = folder;
    }

    public Client getClient() {
        return client;
    }

    public void setClient( Client client ) {
        this.client = client;
    }
}
