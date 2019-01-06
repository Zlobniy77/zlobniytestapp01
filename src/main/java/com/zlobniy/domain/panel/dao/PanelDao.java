package com.zlobniy.domain.panel.dao;

import com.zlobniy.domain.panel.entity.Panel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PanelDao extends JpaRepository<Panel, Long> {

    @Query( "select p from Panel p JOIN FETCH p.client c where c.id = :clientId" )
    List<Panel> findByClientId( @Param ( "clientId" ) Long clientId );

    @Query( "select p from Panel p JOIN FETCH p.folder f where f.id = :folderId" )
    List<Panel> findByFolderId( @Param ( "folderId" ) Long folderId );

    @Query( "select p from Panel p JOIN FETCH p.descriptions d where p.id = :id" )
    Optional<Panel> findById( @Param ( "id" ) Long id );


}
