package com.zlobniy.domain.survey.view.questionnaire;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.zlobniy.domain.survey.entity.questionnaire.ClosedQuestion;
import com.zlobniy.domain.survey.entity.questionnaire.MatrixQuestion;
import com.zlobniy.domain.survey.entity.questionnaire.Question;
import com.zlobniy.domain.survey.entity.questionnaire.TextQuestion;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClosedQuestionView.class, name = "closed"),
        @JsonSubTypes.Type(value = MatrixQuestionView.class, name = "matrix"),
        @JsonSubTypes.Type(value = TextQuestionView.class, name = "text")
})
public class QuestionView {

    private Long id;
    private String title;
    private Integer index;
    private QuestionSettingsView settings;
    private String type;

    public QuestionView(){

    }

    public QuestionView( Question question, String type ){
        setId( question.getId() );
        setTitle( question.getTitle() );
        setIndex( question.getNumber() );
        setType( type );
    }

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

    public Integer getIndex(){
        return index;
    }

    public void setIndex( Integer index ){
        this.index = index;
    }

    public QuestionSettingsView getSettings(){
        return settings;
    }

    public void setSettings( QuestionSettingsView settings ){
        this.settings = settings;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static QuestionView mapQuestion( Question question ) {

        String className = question.getClass().getSimpleName();

        switch ( className ) {
            case "ClosedQuestion":
                return new ClosedQuestionView( (ClosedQuestion) question );
            case "MatrixQuestion":
                return new MatrixQuestionView( (MatrixQuestion) question );
            case "TextQuestion":
                return new TextQuestionView( (TextQuestion) question );
            default:
                return null;
        }

    }

}
