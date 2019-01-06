package com.zlobniy.controller.panel;


import com.zlobniy.domain.panel.service.PanelService;
import com.zlobniy.domain.panel.view.PanelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PanelController {

    private PanelService panelService;

    @Autowired
    public PanelController( PanelService panelService ){
        this.panelService = panelService;
    }

    @RequestMapping( value = "/api/panel/{id}", method = RequestMethod.GET )
    public PanelView find( @PathVariable Long id ){

        return new PanelView();
    }

    @RequestMapping( value = "/api/panel/save/{folderId}", method = RequestMethod.POST )
    public PanelView saveSurvey(
            @RequestBody PanelView panelView,
            @PathVariable Long folderId ) {

        //Folder folder = getFolder( folderId );
        //Client client = findClient();

        System.out.println( panelView );

        return new PanelView();
    }

}
