package com.zlobniy.domain.panel.entity;

import com.zlobniy.domain.client.entity.Client;
import com.zlobniy.domain.folder.entity.Folder;
import com.zlobniy.domain.panel.view.PanelView;
import org.springframework.validation.annotation.Validated;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Validated
public class Panel {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String title;

    @Column
    private Date creationDate;

    @Column
    private Date lastModify;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BgrDescription> descriptions = new ArrayList<>(  );

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BgrData> data = new ArrayList<>(  );

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    @JoinColumn( name = "client_id" )
    @NotNull
    private Client client;

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    @JoinColumn( name = "folder_id" )
    @NotNull
    private Folder folder;


    public Panel(){

    }

    public Panel( PanelView panelView ){
        this.id = panelView.getId();
        this.title = panelView.getTitle();
        this.descriptions = panelView.getHeaders()
                .stream()
                .map( BgrDescription::new )
                .collect(Collectors.toList());

        List<BgrData> data = panelView.getRows()
                .stream()
                .map( b -> new BgrData( b.getCells(), b.getIndex(), "test", descriptions ) )
                .collect(Collectors.toList());

        this.data = data;
        this.creationDate = new Date();
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate( Date creationDate ) {
        this.creationDate = creationDate;
    }

    public Date getLastModify() {
        return lastModify;
    }

    public void setLastModify( Date lastModify ) {
        this.lastModify = lastModify;
    }

    public List<BgrDescription> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions( List<BgrDescription> descriptions ) {
        this.descriptions = descriptions;
    }

    public List<BgrData> getData() {
        return data;
    }

    public void setData( List<BgrData> data ) {
        this.data = data;
    }

    public Client getClient() {
        return client;
    }

    public void setClient( Client client ) {
        this.client = client;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder( Folder folder ) {
        this.folder = folder;
    }
}
