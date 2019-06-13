package com.zlobniy.domain.panel.entity;

import com.zlobniy.domain.panel.BackgroundType;
import com.zlobniy.domain.panel.view.PanelHeaderView;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class BgrDescription {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Integer index;

    @Column
    private BackgroundType type;

    @Column
    private String value;

    @ManyToOne( fetch = FetchType.LAZY )
    private Panel panel;

    public BgrDescription(){

    }

    public BgrDescription( PanelHeaderView value ){
        this.value = value.getTitle();
        this.type = BackgroundType.fromInt( value.getType() );
        this.index = value.getIndex();
    }

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public BackgroundType getType() {
        return type;
    }

    public void setType( BackgroundType type ) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue( String value ) {
        this.value = value;
    }

    public Panel getPanel() {
        return panel;
    }

    public void setPanel( Panel panel ) {
        this.panel = panel;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex( Integer index ) {
        this.index = index;
    }
}
