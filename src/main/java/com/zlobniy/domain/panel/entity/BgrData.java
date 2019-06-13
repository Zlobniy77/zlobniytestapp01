package com.zlobniy.domain.panel.entity;

import com.zlobniy.domain.panel.view.PanelRowValueView;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

    @Column
    private Integer index;

    @OneToMany( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<BgrDataValue> values = new ArrayList<>(  );

    @ManyToOne( fetch = FetchType.LAZY )
    private Panel panel;

    public BgrData(){

    }

    public BgrData( List<PanelRowValueView> rows, Integer index, String respondentId, List<BgrDescription> descriptions ){
        this.respondentId = respondentId;
        this.index = index;
        this.values = rows.stream()
                .map( cell -> new BgrDataValue( cell, descriptions.get( rows.indexOf( cell ) ) ) )
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

    public Integer getIndex() {
        return index;
    }

    public void setIndex( Integer index ) {
        this.index = index;
    }
}
