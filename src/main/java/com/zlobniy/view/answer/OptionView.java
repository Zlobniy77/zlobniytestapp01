package com.zlobniy.view.answer;

import java.util.List;

public class OptionView {

    private Long id;
    private String name;
    private Boolean selected;
    private String value;
    private Integer index;
    private List<ScaleView> scaleGroup;

    public Long getId(){
        return id;
    }

    public void setId( Long id ){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName( String name ){
        this.name = name;
    }

    public Boolean getSelected(){
        return selected;
    }

    public void setSelected( Boolean selected ){
        this.selected = selected;
    }

    public Integer getIndex(){
        return index;
    }

    public void setIndex( Integer index ){
        this.index = index;
    }

    public List<ScaleView> getScaleGroup(){
        return scaleGroup;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setScaleGroup(List<ScaleView> scaleGroup ){
        this.scaleGroup = scaleGroup;
    }
}
