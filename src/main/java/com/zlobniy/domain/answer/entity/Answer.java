package com.zlobniy.domain.answer.entity;

import com.zlobniy.domain.answer.view.AnswerOptionView;
import com.zlobniy.domain.answer.view.AnswerView;
import com.zlobniy.domain.answer.view.ScaleView;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Answer {

    @Id
    @GeneratedValue
    private Long answerId;

    @Column
    private Long questionId;

    @Column
    private Integer questionNumber;

    @OneToMany( fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    @JoinColumn( name = "answerId")
    private List<Element> elements;

    public Answer(){

    }

    public Answer( AnswerView answerView ){
        setAnswerId( answerView.getId() );
        setQuestionId( answerView.getQuestionId() );
        setQuestionNumber( answerView.getQuestionNumber() );

        List<Element> elements = new ArrayList<>(  );
        // sub question (answer alternative)
        for ( AnswerOptionView answerOptionView : answerView.getOptions() ) {
            if( answerOptionView.getSelected() ){
                Element element = new Element(  );
                element.setAnswerOrder( answerOptionView.getIndex() );
                element.setValue( answerOptionView.getValue() );
                elements.add( element );
            }else{
                // scales
                for ( ScaleView scaleView : answerOptionView.getScaleGroup() ) {
                    // scale steps
                    for ( AnswerOptionView view : scaleView.getOptions() ) {
                        if( view.getSelected() ){
                            Element element = new Element( );
                            element.setAnswerOrder( answerOptionView.getIndex() );
                            element.setValue( view.getValue() );
                            element.setScaleGroupOrder( view.getIndex() );
                            element.setScaleOrder( scaleView.getIndex() );
                            elements.add( element );
                        }
                    }
                }
            }

        }

        AnswerOptionView otherField = answerView.getFreeTextOption();
        if( otherField.getValue() != null && !otherField.getValue().isEmpty() ){
            Element element = new Element( );
            element.setAnswerOrder( -1 );
            element.setValue( otherField.getValue() );
            element.setName( otherField.getName() );
            elements.add( element );
        }

        setElements( elements );

    }

    public Long getAnswerId(){
        return answerId;
    }

    public void setAnswerId( Long answerId ){
        this.answerId = answerId;
    }

    public Long getQuestionId(){
        return questionId;
    }

    public void setQuestionId( Long questionId ){
        this.questionId = questionId;
    }

    public List<Element> getElements(){
        return elements;
    }

    public void setElements( List<Element> elements ){
        this.elements = elements;
    }

    public Integer getQuestionNumber(){
        return questionNumber;
    }

    public void setQuestionNumber( Integer questionNumber ){
        this.questionNumber = questionNumber;
    }

    public Answer copy(){
        final Answer answer = new Answer(  );
        answer.setQuestionId( getQuestionId() );
        answer.setQuestionNumber( getQuestionNumber() );
        answer.setElements( getElements().stream().map( Element::copy ).collect( Collectors.toList() ) );

        return answer;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        Answer answer = ( Answer ) o;

        return questionNumber.equals( answer.questionNumber );
    }

    @Override
    public int hashCode() {
        return questionNumber.hashCode();
    }
}
