package com.zlobniy.domain.survey;

import com.zlobniy.domain.survey.entity.Survey;
import com.zlobniy.domain.survey.service.SurveyService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;

@RunWith( SpringJUnit4ClassRunner.class )
@Transactional
@SpringBootTest
public class SurveyServiceTest {

    @Autowired
    private SurveyService surveyService;

    @Test
    public void loadSurveysByFolderId( ){

        long id = 2;

        List<Survey> surveys = surveyService.findSurveysByFolder( id );

        Assert.assertTrue( "surveys not loaded by folder id", surveys.size() > 0 );

    }

    @Test
    public void findSurveysByClient(){

        Long clientId = 1L;

        List<Survey> surveys = surveyService.findByClientId( clientId );

        Assert.assertTrue( "Surveys not loaded by client id", surveys.size() > 0 );

    }

}
