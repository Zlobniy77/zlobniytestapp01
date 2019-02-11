package com.zlobniy.config;

import com.zlobniy.domain.client.entity.Client;
import com.zlobniy.domain.client.service.ClientService;
import com.zlobniy.domain.folder.entity.Folder;
import com.zlobniy.domain.folder.service.FolderService;
import com.zlobniy.domain.panel.entity.BgrData;
import com.zlobniy.domain.panel.entity.BgrDataValue;
import com.zlobniy.domain.panel.entity.BgrDescription;
import com.zlobniy.domain.panel.entity.Panel;
import com.zlobniy.domain.panel.service.PanelService;
import com.zlobniy.domain.survey.entity.Survey;
import com.zlobniy.domain.survey.service.SurveyService;
import com.zlobniy.domain.survey.view.SurveySettingsView;
import com.zlobniy.domain.survey.view.SurveyView;
import com.zlobniy.example.DummyQuestionnaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class InitDefaultData {

    private final ClientService clientService;
    private final SurveyService surveyService;
    private final FolderService folderService;
    private final PanelService panelService;

    @Autowired
    public InitDefaultData( ClientService clientService,
                            SurveyService surveyService,
                            FolderService folderService,
                            PanelService panelService ) {
        this.clientService = clientService;
        this.surveyService = surveyService;
        this.folderService = folderService;
        this.panelService = panelService;
        initData();
    }


    private void initData() {

        System.out.println( "initData " );

        final Client client = new Client();
        client.setUsername( "ans" );
        client.setPassword( "123" );
        client.setEmail( "email" );

        clientService.saveClient( client );

        final Folder homeFolder = generateFolders( client );

        Panel panel = generatePanel( client, homeFolder );

        generate1000Surveys( client, homeFolder );

    }

    private Panel generatePanel( Client client, Folder folder ){

        List<BgrDescription> descriptions = getDescriptions();

        Panel panel = new Panel();
        panel.setTitle( "test panel" );
        panel.setDescriptions( descriptions );
        panel.setData( getData( descriptions ) );
        panel.setClient( client );
        panel.setFolder( folder );
        panel.setCreationDate( new Date(  ) );

        Panel newPanel = panelService.save( panel );

        return newPanel;
    }

    private Folder generateFolders( Client client ){
        final Folder homeFolder = new Folder();
        homeFolder.setClient( client );
        homeFolder.setRoot( true );
        homeFolder.setSelected( true );
        homeFolder.setTitle( client.getUsername() );

        folderService.save( homeFolder );

        final Folder subFolder = new Folder();
        subFolder.setParent( homeFolder );
        subFolder.setClient( client );
        subFolder.setTitle( "folder" );

        final Folder subFolder2 = new Folder();
        subFolder2.setParent( subFolder );
        subFolder2.setClient( client );
        subFolder2.setTitle( "folder 2" );


        folderService.save( subFolder );
        folderService.save( subFolder2 );

        return homeFolder;
    }

    private void generate1000Surveys( Client client, Folder homeFolder ){

        for( int i = 0; i < 50 ; i++ ){
            final DummyQuestionnaire dummyQuestionnaire = new DummyQuestionnaire();
            final SurveyView surveyView = new SurveyView();
            surveyView.setTitle( "Dummy survey " + i );
            surveyView.setQuestionnaire( dummyQuestionnaire.createDummyQuestionnaire( 50 ) );
            surveyView.setSurveySettings( new SurveySettingsView() );
            surveyView.setCreationDate( new Date() );

            final Survey survey = new Survey( surveyView );
            survey.setFolder( homeFolder );
            survey.setClient( client );
            surveyService.save( survey );
        }

    }

    private List<BgrDescription> getDescriptions(){
        List<BgrDescription> descriptions = new ArrayList<>(  );

        BgrDescription mail = new BgrDescription();
        mail.setType( BgrDescription.Type.EMAIL );
        mail.setValue( "email" );

        BgrDescription name = new BgrDescription();
        name.setType( BgrDescription.Type.OPEN );
        name.setValue( "name" );

        BgrDescription dep = new BgrDescription();
        dep.setType( BgrDescription.Type.CLOSED );
        dep.setValue( "department" );

        descriptions.add( mail );
        descriptions.add( name );
        descriptions.add( dep );

        return descriptions;
    }

    private List<BgrData> getData( List<BgrDescription> descriptions ){
        List<BgrData> data = new ArrayList<>(  );

        for( int i = 1; i < 11 ; i++ ){
            String mail = "mail" + i + "@mail.ru";
            String name = "name " + i;
            String dep = "dep ";
            List<BgrDataValue> bgrDataValues = getDataValue( mail, name, dep, descriptions );

            BgrData bgrData = new BgrData();
            bgrData.setValues( bgrDataValues );
            bgrData.setRespondentId( "respondent " + i );

            data.add( bgrData );
        }


        return data;
    }

    private List<BgrDataValue> getDataValue( String emailStr,
                                             String nameStr,
                                             String depStr,
                                             List<BgrDescription> descriptions ){

        List<BgrDataValue> bgrDataValues = new ArrayList<>(  );

        BgrDataValue mail = new BgrDataValue();
        mail.setValue( emailStr );
        mail.setBgrDescription( descriptions.get( 0 ) );

        BgrDataValue name = new BgrDataValue();
        name.setValue( nameStr );
        name.setBgrDescription( descriptions.get( 1 ) );

        BgrDataValue dep = new BgrDataValue();
        dep.setValue( depStr );
        dep.setBgrDescription( descriptions.get( 2 ) );

        bgrDataValues.add( mail );
        bgrDataValues.add( name );
        bgrDataValues.add( dep );

        return bgrDataValues;
    }

}
