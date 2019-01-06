package com.zlobniy.domain.survey.view;

import com.zlobniy.domain.survey.entity.Survey;
import com.zlobniy.util.DateFormatUtil;
import com.zlobniy.view.InfoView;

import java.util.Date;

public class SurveyInfoView implements InfoView {

    private Long id;
    private String title;
    private Date creationDate;

    public SurveyInfoView(){

    }

    public SurveyInfoView( Survey survey ){
        setId( survey.getId() );
        setCreationDate( survey.getCreationDate() );
        setTitle( survey.getTitle() );
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

    public String getCreationDate(){
        return DateFormatUtil.dateToString( this.creationDate );
    }

    @Override
    public String getType() {
        return "Survey";
    }

    public void setCreationDate( Date creationDate ){
        this.creationDate = creationDate;
    }
}
