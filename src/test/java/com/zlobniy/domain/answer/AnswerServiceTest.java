package com.zlobniy.domain.answer;

import com.zlobniy.domain.answer.entity.AnswerSession;
import com.zlobniy.domain.answer.service.AnswerService;
import com.zlobniy.domain.answer.view.AnswerOptionView;
import com.zlobniy.domain.answer.view.AnswerView;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RunWith( SpringJUnit4ClassRunner.class )
@Transactional
@SpringBootTest
public class AnswerServiceTest {

    @Autowired
    private AnswerService answerService;


    @Test
    public void saveAnswersToDataBase(){

        Long surveyId = 1L;

        AnswerView answerView = createDummyAnswer( surveyId, 1, "test" );

        answerService.addAnswer( answerView );

        AnswerSession session = answerService.getAnswers( surveyId, "test" );

        Assert.assertEquals( "Wrong amount of answers ",
                1,
                session.getAnswers().size() );

        AnswerView anotherAnswer = createDummyAnswer( surveyId, 2, "test" );

        answerService.addAnswer( anotherAnswer );

        session = answerService.getAnswers( surveyId, "test" );

        Assert.assertEquals( "Wrong amount of answers after second answer ",
                2,
                session.getAnswers().size() );

        AnswerView reAnswered = createDummyAnswer( surveyId, 2, "test" );

        answerService.addAnswer( reAnswered );

        session = answerService.getAnswers( surveyId, "test" );

        Assert.assertEquals( "Wrong amount of answers after re answering ",
                2,
                session.getAnswers().size() );

    }

    private AnswerView createDummyAnswer( Long surveyId, Integer qNumber, String userId ){
        AnswerView answerView = new AnswerView();
        answerView.setQuestionType( "closed" );
        answerView.setQuestionId( 1L );
        answerView.setQuestionNumber( qNumber );
        answerView.setSurveyId( surveyId );
        answerView.setUserId( userId );
        answerView.setOptions( getElements() );

        return answerView;
    }

    private List<AnswerOptionView> getElements(){
        List<AnswerOptionView> elements = new ArrayList<>(  );
        elements.add( new AnswerOptionView( 1, "1" ) );

        return elements;
    }

}
