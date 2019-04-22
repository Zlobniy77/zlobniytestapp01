package com.zlobniy.domain.answer.view;

import com.zlobniy.domain.answer.entity.Answer;
import com.zlobniy.domain.answer.entity.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnswerView {

    private Long id;
    private String userId;
    private Long surveyId;
    private Long questionId;
    private Integer questionNumber;
    private String questionType;
    private List<AnswerOptionView> options = new ArrayList<>();
    private AnswerOptionView freeTextOption = new AnswerOptionView();

    public AnswerView(){

    }

    public AnswerView( Long surveyId, String userId, Answer answer ){
        setSurveyId( surveyId );
        setUserId( userId );

        setQuestionId( answer.getQuestionId() );
        setQuestionNumber( answer.getQuestionNumber() );

        AnswerOptionView freeTextOption = null;

        Map<Integer,AnswerOptionView> options = new HashMap<>(  );
        // option index,  scale index, list of elements
        Map<Integer,Map<Integer, List<AnswerOptionView>>> scaleOptionsMap = new HashMap<>(  );
        for ( Element element : answer.getElements() ) {



            if( element.getAnswerOrder() == -1 ){
                // freeTextOption
                freeTextOption = new AnswerOptionView(); //other
                freeTextOption.setName( element.getName() );
                freeTextOption.setValue( element.getValue() );

            }else{


                AnswerOptionView option = options.get( element.getAnswerOrder() );
                if( option == null ){
                    option = new AnswerOptionView();
                    option.setSelected( element.getScaleOrder() == -1 );
                    option.setIndex( element.getAnswerOrder() );

                    options.put( element.getAnswerOrder(), option );
                }

                if( element.getScaleOrder() >= 0 ){

                    Map<Integer, List<AnswerOptionView>> stepsMap = scaleOptionsMap.get( element.getAnswerOrder() );
                    if( stepsMap == null ){
                        stepsMap = new HashMap<>(  );
                        List<AnswerOptionView> steps = new ArrayList<>(  );
                        stepsMap.put( element.getScaleOrder(), steps );
                        scaleOptionsMap.put( element.getAnswerOrder(), stepsMap );
                    }

                    List<AnswerOptionView> steps = stepsMap.get( element.getScaleOrder() );
                    if( steps == null ){
                        List<AnswerOptionView> scaleSteps = new ArrayList<>(  );
                        stepsMap.put( element.getScaleOrder(), scaleSteps );
                        steps = scaleSteps;
                    }
                    final AnswerOptionView scaleStep = new AnswerOptionView();
                    scaleStep.setIndex( element.getScaleGroupOrder() );
                    scaleStep.setSelected( true );
                    steps.add( scaleStep );
                }

            }
        }

        List<AnswerOptionView> optionList = new ArrayList<>(  );
        for ( Integer answerOrder : options.keySet() ) {
            AnswerOptionView answerOptionView = new AnswerOptionView();
            answerOptionView.setSelected( options.get( answerOrder ).getSelected() );
            answerOptionView.setIndex( answerOrder );
            List<ScaleView> scales = new ArrayList<>(  );
            Map<Integer, List<AnswerOptionView>> scale = scaleOptionsMap.get( answerOrder );
            if( scale != null ){
                for ( Integer scaleOrder : scale.keySet() ) {
                    ScaleView scaleView = new ScaleView();
                    scaleView.setIndex( scaleOrder );
                    scaleView.setOptions( scale.get( scaleOrder ) );
                    scales.add( scaleView );
                }
            }

            answerOptionView.setScaleGroup( scales );

            optionList.add( answerOptionView );
        }

        setOptions( optionList );

        if( freeTextOption != null ){
            setFreeTextOption( freeTextOption );
        }

    }

    public Long getId(){
        return id;
    }

    public void setId( Long id ){
        this.id = id;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId( String userId ){
        this.userId = userId;
    }

    public Long getSurveyId(){
        return surveyId;
    }

    public void setSurveyId( Long surveyId ){
        this.surveyId = surveyId;
    }

    public List<AnswerOptionView> getOptions(){
        return options;
    }

    public void setOptions( List<AnswerOptionView> options ){
        this.options = options;
    }

    public Long getQuestionId(){
        return questionId;
    }

    public void setQuestionId( Long questionId ){
        this.questionId = questionId;
    }

    public Integer getQuestionNumber(){
        return questionNumber;
    }

    public void setQuestionNumber( Integer questionNumber ){
        this.questionNumber = questionNumber;
    }

    public String getQuestionType(){
        return questionType;
    }

    public void setQuestionType( String questionType ){
        this.questionType = questionType;
    }

    public AnswerOptionView getFreeTextOption() {
        return freeTextOption;
    }

    public void setFreeTextOption(AnswerOptionView freeTextOption) {
        this.freeTextOption = freeTextOption;
    }

    public String toString(){
        return this.id + " " + this.userId + " "+ this.surveyId;
    }

}
