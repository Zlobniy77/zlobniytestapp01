package com.zlobniy.domain.answer.service;

import com.zlobniy.domain.answer.dao.AnswersDao;
import com.zlobniy.domain.answer.entity.Answer;
import com.zlobniy.domain.answer.entity.AnswerSession;
import com.zlobniy.domain.answer.entity.Element;
import com.zlobniy.domain.answer.view.AnswerView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AnswerService {

    private AnswersDao dao;

    @Autowired
    public AnswerService( AnswersDao dao ) {
        this.dao = dao;
    }

    public void addAnswer( AnswerView answerView ) {
        AnswerSession answerSession = dao.getBy( answerView.getSurveyId(), answerView.getUserId() );
        if ( answerSession == null ) {
            answerSession = new AnswerSession( answerView );
        } else {
            addOrReplaceAnswer( answerSession, new Answer( answerView ) );

        }
        dao.save( answerSession );
    }

    private void addOrReplaceAnswer( AnswerSession answerSession, Answer answer ) {
        // if answer already exist on this question we replace this answer to new one.
        if ( answerSession.getAnswers().contains( answer ) ) {
            List<Answer> answers = new ArrayList<>();
            for ( Answer ans : answerSession.getAnswers() ) {
                if ( !Objects.equals( ans.getQuestionNumber(), answer.getQuestionNumber() ) ) {
                    answers.add( ans );
                } else {
                    answers.add( answer );
                }
            }
            answerSession.setAnswers( answers );
        } else {
            // answer not exist and we just add a new answer to answer session.
            answerSession.getAnswers().add( answer );
        }

    }

    /**
     * Call this method when respondent start answering on survey, or re answering
     */
    public AnswerSession prepareSession( Long surveyId, String userId ) {
        final AnswerSession answerSession = dao.getBy( surveyId, userId );
        if ( answerSession == null ) return null;

        answerSession.setDeleted( true );
        dao.save( answerSession );

        final AnswerSession copySession = answerSession.copy();
        dao.save( copySession );

        return copySession;
    }

    public AnswerSession getAnswers( Long surveyId, String userId ) {
        return dao.getBy( surveyId, userId );
    }

    public List<AnswerSession> getSesions( Long surveyId ){
        List<AnswerSession> sessions = dao.findBySurveyId( surveyId );
        return sessions;
    }

    public void updateAnswersElements( Long surveyId,
                                       String userId,
                                       Integer questionNumber,
                                       String value,
                                       String extraInfo ){

        AnswerSession answerSession = dao.getBy( surveyId, userId );
        List<Answer> answers = answerSession.getAnswers()
                .stream()
                .filter( a -> Objects.equals( a.getQuestionNumber(), questionNumber ) )
                .collect( Collectors.toList() );

        for ( Answer answer : answers ) {
            List<Element> elements = new ArrayList<>(  );
            Element element = new Element(  );
            element.setValue( value );
            element.setExtraInfo( extraInfo );
            elements.add( element );

            answer.setElements( elements );
        }

        dao.save( answerSession );
    }


    public List<AnswerView> loadAnswers( Long surveyId ) {
        AnswerSession answerSession = dao.getOne( surveyId );

        List<AnswerView> answers = new ArrayList<>(  );
        for ( Answer answer : answerSession.getAnswers() ) {
            answers.add( new AnswerView( answerSession.getSurveyId(), answerSession.getUserId(), answer ) );
        }

        return answers;
    }

}
