package com.zlobniy.domain.panel.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class BgrData {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String respondentId;

    @OneToMany( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<BgrDataValue> values = new ArrayList<>(  );

    @ManyToOne( fetch = FetchType.LAZY )
    private Panel panel;

    public BgrData(){

    }

    public BgrData( List<String> rows, String respondentId, List<BgrDescription> descriptions ){
        this.respondentId = respondentId;
        this.values = rows.stream()
                .map( s -> new BgrDataValue( s, descriptions.get( rows.indexOf( s ) ) ) )
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public List<BgrDataValue> getValues() {
        return values;
    }

    public void setValues( List<BgrDataValue> values ) {
        this.values = values;
    }

    public Panel getPanel() {
        return panel;
    }

    public void setPanel( Panel panel ) {
        this.panel = panel;
    }

    public String getRespondentId() {
        return respondentId;
    }

    public void setRespondentId( String respondentId ) {
        this.respondentId = respondentId;
    }
}
