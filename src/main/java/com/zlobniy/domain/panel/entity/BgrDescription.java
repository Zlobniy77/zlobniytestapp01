package com.zlobniy.domain.panel.entity;

import javax.persistence.*;

@Entity
public class BgrDescription {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Type type;

    @Column
    private String value;

    @ManyToOne( fetch = FetchType.LAZY )
    private Panel panel;

    public BgrDescription(){

    }

    public BgrDescription( String value ){
        this.value = value;
        this.type = Type.CLOSED;
    }

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType( Type type ) {
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

    public enum Type{
        EMAIL,
        PHONE,
        CLOSED,
        OPEN
    }
}
