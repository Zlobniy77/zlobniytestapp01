package com.zlobniy.controller.panel;


import com.zlobniy.domain.client.entity.Client;
import com.zlobniy.domain.client.view.ClientView;
import com.zlobniy.domain.folder.entity.Folder;
import com.zlobniy.domain.folder.service.FolderService;
import com.zlobniy.domain.panel.entity.Panel;
import com.zlobniy.domain.panel.service.PanelService;
import com.zlobniy.domain.panel.view.PanelInfoView;
import com.zlobniy.domain.panel.view.PanelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PanelController {

    private PanelService panelService;
    private FolderService folderService;

    @Autowired
    public PanelController( PanelService panelService, FolderService folderService ){
        this.panelService = panelService;
        this.folderService = folderService;
    }

    @RequestMapping( value = "/api/panel/{id}", method = RequestMethod.GET )
    public PanelView find( @PathVariable Long id ){

        Panel panel = panelService.findFull( id );

        return new PanelView( panel );
    }

    @RequestMapping( value = "/api/panel/save/{folderId}", method = RequestMethod.POST )
    public void savePanel(
            @RequestBody PanelView panelView,
            @PathVariable Long folderId ) {

        Folder folder = folderService.findById( folderId );
        ClientView clientView = (ClientView) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Panel panel = new Panel( panelView );
        panel.setFolder( folder );
        panel.setClient( new Client( clientView ) );

        System.out.println( panelView );

        panelService.save( panel );
    }

    @RequestMapping( value = "/api/panel/all/{id}", method = RequestMethod.GET )
    public List<PanelInfoView> loadAllForClient( @PathVariable Long id ){

        List<Panel> panels = panelService.findByClientId( id );

        List<PanelInfoView> panelViews = panels.stream().map( PanelInfoView::new ).collect( Collectors.toList() );


        return panelViews;
    }

}
