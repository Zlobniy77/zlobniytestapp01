package com.zlobniy.controller.phone;


import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.zlobniy.domain.client.entity.Client;
import com.zlobniy.domain.client.service.ClientService;
import com.zlobniy.domain.export.ExportAnswerView;
import com.zlobniy.domain.implementation.view.PhoneImplRequestView;
import com.zlobniy.domain.panel.entity.Panel;
import com.zlobniy.domain.panel.service.PanelService;
import com.zlobniy.domain.survey.entity.Survey;
import com.zlobniy.domain.survey.service.SurveyService;
import com.zlobniy.twilio.survey.controllers.PhoneSurveyController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
public class PhoneController {

    private ClientService clientService;
    private PanelService panelService;
    private SurveyService surveyService;

    @Autowired
    public PhoneController( ClientService clientService, PanelService panelService, SurveyService surveyService ){
        this.clientService = clientService;
        this.panelService = panelService;
        this.surveyService = surveyService;
    }

    @RequestMapping( value = "/api/startSurvey", method = RequestMethod.POST )
    public String startSurvey( @RequestBody PhoneImplRequestView view ){

        Client client = clientService.find( view.getClientId() );
        Panel panel = panelService.findFull( view.getPanelId() );
        Survey survey = surveyService.find( view.getSurveyId() );

        // acc telefon0000001@mail.ru
        String sid = client.getProperty().getSid();
        String token = client.getProperty().getToken();
        String twilioPhone = client.getProperty().getPhoneNumber();
        String myPhone = panel.getData().get( 0 ).getValues().get( 0 ).getValue();

        PhoneSurveyController.addToRunner( twilioPhone, survey );

        Twilio.init( sid, token );
        Call call = Call.creator(
                new com.twilio.type.PhoneNumber( myPhone ),
                new com.twilio.type.PhoneNumber( twilioPhone ),
                URI.create("http://188.242.130.250:4567/interview"))
                .create();

        System.out.println( call.getAccountSid() );

        return "start survey";
    }

    @RequestMapping( value = "/api/results/{id}", method = RequestMethod.GET )
    public List<ExportAnswerView> getResults( @PathVariable Long id ){
        return PhoneSurveyController.getResults( id );
    }


    @RequestMapping( value = "/readSms", method = RequestMethod.POST )
    public String readSms( @RequestBody PhoneRequest phoneRequest ){

        Twilio.init( phoneRequest.getSid(), phoneRequest.getToken() );

        ResourceSet<Message> messages = Message.reader( phoneRequest.getSid() ).read(  );

        for ( Message message : messages ) {
            System.out.println( message );
            if( message.getDirection().equals( Message.Direction.INBOUND ) ){
                System.out.println( "incoming message: " + message.getBody() );
            }
        }

        return "call my baby";

    }

    @RequestMapping( value = "/sendSms", method = RequestMethod.POST )
    public String sendSms( @RequestBody PhoneRequest phoneRequest, HttpServletRequest request ){

        Twilio.init( phoneRequest.getSid(), phoneRequest.getToken() );

        Message message = Message
                .creator(new PhoneNumber( phoneRequest.getTo() ),
                        new PhoneNumber( phoneRequest.getFrom() ),
                        phoneRequest.getMessage() )
                .create();

        System.out.println(message.getSid());

        return "call me baby";

    }

    @RequestMapping( value = "/sendSms2", method = RequestMethod.POST )
    public String sendSms2( HttpServletRequest request ){



        return "call me baby";

    }

}
