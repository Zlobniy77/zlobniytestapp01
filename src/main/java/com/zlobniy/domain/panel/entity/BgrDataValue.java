package com.zlobniy.domain.panel.entity;

import com.zlobniy.domain.panel.view.PanelRowValueView;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class BgrDataValue {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private BgrDescription bgrDescription;

    @ManyToOne( fetch = FetchType.LAZY)
    private BgrData bgrData;

    @Column
    private Integer columnIndex;

    @Column
    private String value;

    public BgrDataValue(){

    }

    public BgrDataValue( PanelRowValueView value, BgrDescription description ){
        this.value = value.getTitle();
        this.columnIndex = value.getIndex();
        this.bgrDescription = description;
    }

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public BgrDescription getBgrDescription() {
        return bgrDescription;
    }

    public void setBgrDescription( BgrDescription bgrDescription ) {
        this.bgrDescription = bgrDescription;
    }

    public BgrData getBgrData() {
        return bgrData;
    }

    public void setBgrData( BgrData bgrData ) {
        this.bgrData = bgrData;
    }

    public String getValue() {
        return value;
    }

    public void setValue( String value ) {
        this.value = value;
    }

    public Integer getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex( Integer columnIndex ) {
        this.columnIndex = columnIndex;
    }
}
