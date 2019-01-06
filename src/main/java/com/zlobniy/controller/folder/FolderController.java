package com.zlobniy.controller.folder;

import com.zlobniy.domain.client.view.ClientView;
import com.zlobniy.domain.folder.entity.Folder;
import com.zlobniy.domain.folder.service.FolderService;
import com.zlobniy.domain.folder.view.FolderView;
import com.zlobniy.domain.panel.service.PanelService;
import com.zlobniy.domain.survey.service.SurveyService;
import com.zlobniy.view.InfoView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FolderController {

    private FolderService folderService;
    private SurveyService surveyService;
    private PanelService panelService;

    @Autowired
    public FolderController( FolderService folderService,
                             SurveyService surveyService,
                             PanelService panelService ) {
        this.folderService = folderService;
        this.surveyService = surveyService;
        this.panelService = panelService;
    }

    @RequestMapping( value = "/api/folder/loadAll", method = RequestMethod.GET )
    public List<FolderView> loadFolders( HttpServletRequest request ) {

        ClientView clientView = (ClientView) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Folder> folders = folderService.findByClientId( clientView.getId() );
        List<FolderView> folderViews = folders.stream().map( FolderView::new ).collect( Collectors.toList() );

        return folderViews;
    }

    // load all data in folder, currently only surveys but then...
    @RequestMapping( value = "/api/folder/load/{id}", method = RequestMethod.GET )
    public List<InfoView> loadData( @PathVariable Long id ) {

        List<InfoView> data = new ArrayList<>(  );

        data.addAll( surveyService.findLightSurveysByFolder( id ) );
        data.addAll( panelService.findLightByFolder( id ) );

        return data;
    }

    @RequestMapping( value = "/api/folder/{id}/{param}/{value}", method = RequestMethod.PUT )
    public void updateFolderStatus( @PathVariable Long id, @PathVariable String param, @PathVariable Boolean value ) {
        Folder folder = folderService.findById( id );

        switch ( param ) {
            case "expand":
                folder.setExpanded( value );
                break;
            case "select":
                // bad solution. rewrite
                ClientView clientView = (ClientView) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();
                List<Folder> selected = folderService.findSelected( clientView.getId() );
                for ( Folder folder1 : selected ) {
                    folder1.setSelected( false );
                    folderService.saveFolder( folder1 );
                }

                folder.setSelected( value );

            default:
                System.out.println( "unexpected folder update" );
        }

        folderService.saveFolder( folder );
    }


}
