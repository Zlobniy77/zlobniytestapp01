package com.zlobniy.entity.survey.questionnaire;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClosedQuestion.class, name = "closed"),
        @JsonSubTypes.Type(value = MatrixQuestion.class, name = "matrix")
})
public class Question {

    private Long id;
    private String title;
    private String layout;

    public Long getId(){
        return id;
    }

    public void setId( Long id ){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle( String title ){
        this.title = title;
    }

    public void setLayout( String layout ){
        this.layout = layout;
    }

    public String getLayout(){
        return layout;
    }

}
