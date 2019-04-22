package com.zlobniy.domain.answer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Element {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private String value;

    @Column
    private Integer answerOrder;

    @Column
    private Integer scaleOrder = -1;

    @Column
    private Integer scaleGroupOrder = -1;

    public Element(){

    }

    public Element( String name, String value, Integer answerOrder ){
        this.name = name;
        this.value = value;
        this.answerOrder = answerOrder;
    }

    public String getName(){
        return name;
    }

    public void setName( String name ){
        this.name = name;
    }

    public String getValue(){
        return value;
    }

    public void setValue( String value ){
        this.value = value;
    }

    public Integer getAnswerOrder(){
        return answerOrder;
    }

    public void setAnswerOrder( Integer answerOrder ){
        this.answerOrder = answerOrder;
    }

    public Integer getScaleOrder(){
        return scaleOrder;
    }

    public void setScaleOrder( Integer scaleOrder ){
        this.scaleOrder = scaleOrder;
    }

    public Integer getScaleGroupOrder(){
        return scaleGroupOrder;
    }

    public void setScaleGroupOrder( Integer scaleGroupOrder ){
        this.scaleGroupOrder = scaleGroupOrder;
    }

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public Element copy() {
        final Element element = new Element();
        element.setAnswerOrder( getAnswerOrder() );
        element.setName( getName() );
        element.setScaleGroupOrder( getScaleGroupOrder() );
        element.setScaleOrder( getScaleOrder() );
        element.setValue( getValue() );

        return element;
    }
}
