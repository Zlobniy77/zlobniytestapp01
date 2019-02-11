package com.zlobniy.domain.folder.entity;

import com.zlobniy.domain.client.entity.Client;
import com.zlobniy.domain.folder.view.FolderView;
import com.zlobniy.domain.panel.entity.Panel;
import com.zlobniy.domain.survey.entity.Survey;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Folder {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String title;

    @ManyToOne( fetch = FetchType.LAZY )
    private Folder parent;

    @OneToMany( mappedBy = "parent" ,fetch = FetchType.LAZY )
    private List<Folder> children = new ArrayList<>(  );

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    @JoinColumn( name = "client_id" )
    private Client client;

    @Column
    private boolean expanded;

    @Column
    private boolean selected;

    @Column
    private boolean isRoot;

    /**
     * for each object we have a list of elements contains in current folder
     * */
    @OneToMany( mappedBy = "folder", fetch = FetchType.LAZY )
    private List<Survey> surveys = new ArrayList<>();

    @OneToMany( mappedBy = "folder", fetch = FetchType.LAZY )
    private List<Panel> panels = new ArrayList<>(  );

    public Folder( FolderView folderView ){
        this.id = folderView.getId();
        this.title = folderView.getTitle();
        this.expanded = folderView.isExpanded();
        this.selected = folderView.isSelected();
        this.isRoot = false;
        this.children = folderView.getChildren().stream().map( Folder::new ).collect( Collectors.toList() );
    }

    public Folder(){

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

    public List<Folder> getChildren() {
        return children;
    }

    public void setChildren( List<Folder> children ) {
        this.children = children;
    }

    public boolean getRoot() {
        return isRoot;
    }

    public void setRoot( boolean root ) {
        isRoot = root;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded( boolean expanded ) {
        this.expanded = expanded;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected( boolean selected ) {
        this.selected = selected;
    }

    public List<Panel> getPanels() {
        return panels;
    }

    public void setPanels( List<Panel> panels ) {
        this.panels = panels;
    }
}
