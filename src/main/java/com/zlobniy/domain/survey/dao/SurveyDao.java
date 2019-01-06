package com.zlobniy.domain.survey.dao;

import com.zlobniy.domain.survey.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyDao extends JpaRepository<Survey, Long> {


    @Query( "select s from Survey s JOIN FETCH s.questionnaire q JOIN FETCH s.surveySettings g where s.id = :id" )
    Survey findByIdEager( @Param( "id" ) Long id );

    @Query( "select s from Survey s JOIN FETCH s.questionnaire q JOIN FETCH s.surveySettings g where folder_id = :id" )
    List<Survey> findAllInFolder( @Param( "id" ) Long id );

    @Query( "select s from Survey s where folder_id = :id" )
    List<Survey> findAllInFolderLight( @Param( "id" ) Long id );

    @Query( "select s from Survey s JOIN FETCH s.client c where c.id = :clientId" )
    List<Survey> findByClientId( @Param ( "clientId" ) Long clientId );


}
