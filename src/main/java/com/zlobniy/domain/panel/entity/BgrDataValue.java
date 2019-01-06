package com.zlobniy.domain.panel.entity;

import javax.persistence.*;
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
    private String value;

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
}
