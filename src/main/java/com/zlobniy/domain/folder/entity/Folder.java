package com.zlobniy.domain.folder.entity;

import com.zlobniy.domain.client.entity.Client;
import com.zlobniy.domain.survey.entity.Survey;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Folder {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String title;

    @ManyToOne( fetch = FetchType.LAZY )
    private Folder parent;

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    @JoinColumn( name = "client_id" )
    private Client client;

    /**
     * for each object we have a list of elements contains in current folder
     * */
    @OneToMany( mappedBy = "folder", fetch = FetchType.LAZY )
    private List<Survey> surveys = new ArrayList<>();


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

    public List<Survey> getSurveys() {
        return surveys;
    }

    public void setSurveys( List<Survey> surveys ) {
        this.surveys = surveys;
    }

    public Folder getParent() {
        return parent;
    }

    public void setParent( Folder parent ) {
        this.parent = parent;
    }

    public Client getClient() {
        return client;
    }

    public void setClient( Client client ) {
        this.client = client;
    }

}