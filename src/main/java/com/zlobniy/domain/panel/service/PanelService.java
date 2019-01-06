package com.zlobniy.domain.panel.service;

import com.zlobniy.domain.panel.dao.PanelDao;
import com.zlobniy.domain.panel.entity.Panel;
import com.zlobniy.domain.panel.view.PanelInfoView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PanelService {

    private PanelDao dao;

    @Autowired
    public PanelService( PanelDao dao ){
        this.dao = dao;
    }

    public Panel find( Long id ){
        return dao.getOne( id );
    }

    public Panel findFull( Long id ){
        return dao.findById( id ).get();
    }

    public List<Panel> findAll(){
        return dao.findAll();
    }

    public Panel save( Panel panel ){
        return dao.save( panel );
    }

    public List<Panel> findByClientId( Long clientId ){
        return dao.findByClientId( clientId );
    }

    public List<Panel> findByFolderId( Long folderId ){
        return dao.findByFolderId( folderId );
    }

    @Transactional
    public List<PanelInfoView> findLightByFolder( Long folderId ){

        List<PanelInfoView> panels = dao.findByFolderId( folderId )
                .stream()
                .map( PanelInfoView::new )
                .collect(Collectors.toList());

        return panels;
    }

}
