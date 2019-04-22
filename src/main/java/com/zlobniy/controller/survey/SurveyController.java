package com.zlobniy.controller.survey;

import com.zlobniy.domain.answer.entity.Answer;
import com.zlobniy.domain.answer.entity.AnswerSession;
import com.zlobniy.domain.answer.service.AnswerService;
import com.zlobniy.domain.answer.view.AnswerView;
import com.zlobniy.domain.client.entity.Client;
import com.zlobniy.domain.client.service.ClientService;
import com.zlobniy.domain.client.view.ClientView;
import com.zlobniy.domain.folder.entity.Folder;
import com.zlobniy.domain.folder.service.FolderService;
import com.zlobniy.domain.survey.entity.Survey;
import com.zlobniy.domain.survey.service.SurveyService;
import com.zlobniy.domain.survey.view.RespondentSurveyView;
import com.zlobniy.domain.survey.view.SurveyLinkView;
import com.zlobniy.domain.survey.view.SurveyView;
import com.zlobniy.util.Checksum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class SurveyController {

    private SurveyService surveyService;
    private AnswerService answerService;
    private ClientService clientService;
    private FolderService folderService;

    @Autowired
    public SurveyController( SurveyService surveyService,
                             AnswerService answerService,
                             ClientService clientService,
                             FolderService folderService ) {

        this.surveyService = surveyService;
        this.answerService = answerService;
        this.clientService = clientService;
        this.folderService = folderService;
    }

    @RequestMapping( value = "/api/survey/{id}", method = RequestMethod.GET )
    public SurveyView loadSurvey( @PathVariable( "id" ) Long id, HttpServletRequest request ) {
        return surveyService.findById( id );
    }

    /**
     * Load survey with answers
     */
    @RequestMapping( value = "/api/respondentSurvey/{id}", method = RequestMethod.GET )
    public RespondentSurveyView loadRespondentSurvey( @PathVariable( "id" ) Long id, HttpServletRequest request ) {
        Long surveyId = id;
        String userId = "test";

        AnswerSession session = answerService.prepareSession( surveyId, userId );
        SurveyView surveyView = surveyService.findById( surveyId );

        List<AnswerView> answers = new ArrayList<>();
        if ( session != null ) {
            for ( Answer answer : session.getAnswers() ) {
                answers.add( new AnswerView( session.getSurveyId(), session.getUserId(), answer ) );
            }

        }

        RespondentSurveyView respondentSurveyView = new RespondentSurveyView();
        respondentSurveyView.setAnswers( answers );
        respondentSurveyView.setSurveyView( surveyService.findById( surveyId ) );
        return respondentSurveyView;
    }

    /**
     * Load survey with answers
     */
    @RequestMapping( value = "/realRespondentSurvey/{checksum}", method = RequestMethod.GET )
    public RespondentSurveyView loadRealRespondentSurvey( @PathVariable( "checksum" ) String checksum ) {

        //get data from checksum
        Checksum checksum1 = new Checksum( checksum );
        Long surveyId = checksum1.getSurveyId();
        String userId = checksum1.getUserId();

        //validate data
        System.out.println( surveyId );
        System.out.println( userId );

        AnswerSession session = answerService.prepareSession( surveyId, userId );
        SurveyView surveyView = surveyService.findById( surveyId );

        List<AnswerView> answers = new ArrayList<>();
        if ( session != null ) {
            for ( Answer answer : session.getAnswers() ) {
                answers.add( new AnswerView( session.getSurveyId(), session.getUserId(), answer ) );
            }

        }

        // find survey and answers if exist and load
        RespondentSurveyView respondentSurveyView = new RespondentSurveyView();
        respondentSurveyView.setUserId( userId );
        respondentSurveyView.setAnswers( answers );
        respondentSurveyView.setSurveyView( surveyView );
        return respondentSurveyView;
    }

    @RequestMapping( value = "/api/getSurveyLink/{id}", method = RequestMethod.GET )
    public SurveyLinkView getSurveyLink( @PathVariable( "id" ) Long id, HttpServletRequest request ) {
        SurveyLinkView link = new SurveyLinkView();

        String serverName = request.getServerName();
        String port = !serverName.contains( "openshiftapps" ) ? ":" + request.getLocalPort() : "";

        String url1 = "http://" + serverName + port + "/survey-viewer/" + Checksum.generateChecksum( id, "1" );
        String url2 = "http://" + serverName + port + "/survey-viewer/" + Checksum.generateChecksum( id, "2" );
        String url3 = "http://" + serverName + port + "/survey-viewer/" + Checksum.generateChecksum( id, "3" );
        String url4 = "http://" + serverName + port + "/survey-viewer/" + Checksum.generateChecksum( id, "4" );
        link.addLink( url1 );
        link.addLink( url2 );
        link.addLink( url3 );
        link.addLink( url4 );

        return link;
    }

    @RequestMapping( value = "/api/saveSurvey/{folderId}", method = RequestMethod.POST )
    public SurveyView saveSurvey( @RequestBody SurveyView surveyView, @PathVariable Long folderId ) {

        Folder folder = getFolder( folderId );
        Client client = findClient();

        surveyView.setCreationDate( new Date() );

        Survey survey = new Survey( surveyView );
        survey.setFolder( folder );
        survey.setClient( client );

        SurveyView savedSurveyView = new SurveyView( surveyService.save( survey ) );

        return savedSurveyView;
    }

    @RequestMapping( value = "/saveAnswers", method = RequestMethod.POST )
    public String saveAnswers( @RequestBody AnswerView answerView ) {
        answerService.addAnswer( answerView );

        System.out.println( answerView );
        return answerView.toString();
    }

    @RequestMapping( value = "/api/answers/{surveyId}", method = RequestMethod.GET )
    public List<AnswerView> loadAnswers( @PathVariable( "surveyId" ) Long surveyId ) {
        return answerService.loadAnswers( surveyId );
    }

    private Folder getFolder( Long folderId ) {

        Folder folder = folderService.findById( folderId );
        // todo check owner of folder.

        //Client client = clientService.findWithFolders( clientView.getId() );
        //Folder folder = client.getFolders().get( 0 );

        return folder;
    }

    private Client findClient(){
        ClientView clientView = (ClientView) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return new Client( clientView );
    }


}
