package com.zlobniy.domain.answer.view;

import java.util.ArrayList;
import java.util.List;

public class ScaleView {

    private Integer index;
    private String name;
    private List<AnswerOptionView> options = new ArrayList<>(  );

    public Integer getIndex(){
        return index;
    }

    public void setIndex( Integer index ){
        this.index = index;
    }

    public List<AnswerOptionView> getOptions(){
        return options;
    }

    public void setOptions( List<AnswerOptionView> options ){
        this.options = options;
    }

    public String getName(){
        return name;
    }

    public void setName( String name ){
        this.name = name;
    }
}
