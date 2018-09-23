package com.zlobniy.domain.answer.dao;

import com.zlobniy.domain.answer.entity.AnswerSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnsDao extends JpaRepository<AnswerSession, Long> {

    @Query( "select a from AnswerSession a where a.surveyId = :surveyId and a.userId = :userId" )
    AnswerSession getBy( @Param( "surveyId" ) Long surveyId, @Param( "userId" ) String userId );

}
