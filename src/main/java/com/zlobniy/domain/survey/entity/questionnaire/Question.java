package com.zlobniy.domain.survey.entity.questionnaire;

import com.zlobniy.domain.survey.view.questionnaire.ClosedQuestionView;
import com.zlobniy.domain.survey.view.questionnaire.MatrixQuestionView;
import com.zlobniy.domain.survey.view.questionnaire.QuestionView;
import com.zlobniy.domain.survey.view.questionnaire.TextQuestionView;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column
    private String title;

    @Column
    private Integer number;

    @ManyToOne(fetch = FetchType.LAZY)
    private Questionnaire questionnaire;

    public Question(){

    }

    public Question( QuestionView questionView ){
        setId( questionView.getId() );
        setTitle( questionView.getTitle() );
        setNumber( questionView.getIndex() );
    }

    public Long getId(){
        return id;
    }

    public void setId( Long id ){
        this.id = id;
    }

    public Questionnaire getQuestionnaire(){
        return questionnaire;
    }

    public void setQuestionnaire( Questionnaire questionnaire ){
        this.questionnaire = questionnaire;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle( String title ){
        this.title = title;
    }

    public Integer getNumber(){
        return number;
    }

    public void setNumber( Integer number ){
        this.number = number;
    }

    public static Question mapQuestion( QuestionView questionView ){

        switch( questionView.getType() ){
            case "closed":
                return new ClosedQuestion( (ClosedQuestionView) questionView );
            case "matrix":
                return new MatrixQuestion( (MatrixQuestionView) questionView );
            case "text":
                return new TextQuestion( (TextQuestionView) questionView );
            default:
                return null;
        }

    }


}
