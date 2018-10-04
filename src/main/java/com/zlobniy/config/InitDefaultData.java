package com.zlobniy.config;

import com.zlobniy.domain.client.entity.Client;
import com.zlobniy.domain.client.service.ClientService;
import com.zlobniy.domain.folder.entity.Folder;
import com.zlobniy.domain.folder.service.FolderService;
import com.zlobniy.domain.survey.entity.Survey;
import com.zlobniy.domain.survey.service.SurveyService;
import com.zlobniy.domain.survey.view.SurveySettingsView;
import com.zlobniy.domain.survey.view.SurveyView;
import com.zlobniy.example.DummyQuestionnaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class InitDefaultData {

    private final ClientService clientService;
    private final SurveyService surveyService;
    private final FolderService folderService;

    @Autowired
    public InitDefaultData( ClientService clientService, SurveyService surveyService, FolderService folderService ) {
        this.clientService = clientService;
        this.surveyService = surveyService;
        this.folderService = folderService;
        initData();
    }


    private void initData() {

        System.out.println( "initData " );

        final Client client = new Client();
        client.setUsername( "ans" );
        client.setPassword( "123" );
        client.setEmail( "email" );

        clientService.saveClient( client );

        final Folder homeFolder = new Folder();
        homeFolder.setClient( client );
        homeFolder.setTitle( "Test client home folder" );

        folderService.saveFolder( homeFolder );

        final DummyQuestionnaire dummyQuestionnaire = new DummyQuestionnaire();

        final SurveyView surveyView = new SurveyView();
        surveyView.setTitle( "Dummy survey" );
        surveyView.setQuestionnaire( dummyQuestionnaire.createDummyQuestionnaire() );
        surveyView.setSurveySettings( new SurveySettingsView() );
        surveyView.setCreationDate( new Date() );

        final Survey survey = new Survey( surveyView );
        survey.setFolder( homeFolder );
        surveyService.save( survey );

        List<Survey> storedSurveys = surveyService.findAll();
        List<Folder> folders = folderService.findAll();
        List<Folder> folders2 = folderService.findAllEager();

        Client clt = clientService.find( client.getId() );
        Client clientWithFolders = clientService.findWithFolders( client.getId() );

        Folder folderWithSurveys = folderService.findWithSurveys( homeFolder.getId() );

        System.out.println( storedSurveys );

    }

}