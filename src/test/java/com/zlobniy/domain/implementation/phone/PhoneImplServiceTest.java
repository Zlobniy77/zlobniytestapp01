package com.zlobniy.domain.implementation.phone;

import com.zlobniy.domain.answer.view.AnswerView;
import com.zlobniy.domain.implementation.view.IncomingCall;
import com.zlobniy.domain.survey.entity.Survey;
import com.zlobniy.domain.survey.service.SurveyService;
import com.zlobniy.domain.survey.view.PhoneSurveyView;
import com.zlobniy.example.DummySurvey;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

@RunWith( SpringJUnit4ClassRunner.class )
@Transactional
@SpringBootTest
public class PhoneImplServiceTest {

    @Autowired
    private PhoneImplService service;

    @Autowired
    private SurveyService surveyService;

    @Test
    public void processClosedQuestionAnswersTest(){

        Survey survey = surveyService.findAll().get( 0 );

        IncomingCall call = new IncomingCall(  );
        call.setDigits( "1" );

        PhoneSurveyView surveyView = new PhoneSurveyView( survey );

        AnswerView answerView = service.processAnswer( call, surveyView );

        Assert.assertEquals( "Amount of answer options is wrong ", 3,  answerView.getOptions().size() );
        Assert.assertTrue( "Selected not first alternative ", answerView.getOptions().get( 0 ).getSelected() );

    }

    @Test
    public void processOpenQuestionAnswers(){

        DummySurvey dummySurveys = new DummySurvey();

        Survey survey = dummySurveys.createSurveyWithOpenQuestions();
        IncomingCall call = new IncomingCall(  );
        call.setRecordingUrl( "url for save open answer" );
        call.setTranscriptionText( "decoded text" );

        PhoneSurveyView surveyView = new PhoneSurveyView( survey );

        AnswerView answerView = service.processAnswer( call, surveyView );

        System.out.println( answerView );

    }

}
