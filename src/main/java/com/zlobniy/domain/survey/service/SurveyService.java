package com.zlobniy.domain.survey.service;

import com.zlobniy.domain.survey.dao.SurveyDao;
import com.zlobniy.domain.survey.entity.Survey;
import com.zlobniy.domain.survey.view.SurveyInfoView;
import com.zlobniy.domain.survey.view.SurveyView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SurveyService {

    private final SurveyDao surveyDao;

    @Autowired
    public SurveyService( SurveyDao surveyDao ) {
        this.surveyDao = surveyDao;
    }

    public SurveyView findById( Long id ) {
       final Survey survey = surveyDao.findByIdEager( id );

        return new SurveyView( survey );
    }

//    public List<SurveyInfoView> getAllSurveys() {
//        final List<Survey> surveys = surveyDao.findAll();
//        final List<SurveyInfoView> surveysInfo = new ArrayList<>();
//        for ( Survey survey : surveys ) {
//            surveysInfo.add( new SurveyInfoView( survey ) );
//        }
//
//        return surveysInfo;
//    }

    @Transactional
    public List<SurveyView> findSurveyViewsByFolder( Long folderId ){

        List<Survey> surveys = surveyDao.findAllInFolder( folderId );
        List<SurveyView> surveyViews = surveys.stream().map( SurveyView::new ).collect(Collectors.toList());

        return surveyViews;
    }

    @Transactional
    public List<Survey> findSurveysByFolder( Long folderId ){

        List<Survey> surveys = surveyDao.findAllInFolder( folderId );

        return surveys;
    }

    public List<Survey> findByClientId( Long clientId ){

        List<Survey> surveys = surveyDao.findByClientId( clientId );

        return surveys;
    }

    @Transactional
    public List<SurveyInfoView> findLightSurveysByFolder( Long folderId ){

        List<SurveyInfoView> surveys = surveyDao.findAllInFolderLight( folderId )
                .stream()
                .map( SurveyInfoView::new )
                .collect(Collectors.toList());

        return surveys;
    }

    public List<Survey> findAll(){
        return surveyDao.findAll();
    }

    public Survey save( Survey survey ){
        surveyDao.save( survey );
        return survey;
    }

    public SurveyView save( SurveyView surveyView ) {
        final Survey survey = surveyDao.save( new Survey( surveyView ) );
        return new SurveyView( survey );
    }

}
