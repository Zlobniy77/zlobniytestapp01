package com.zlobniy.controller.phone;


import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.type.PhoneNumber;
import com.zlobniy.domain.client.entity.Client;
import com.zlobniy.domain.client.service.ClientService;
import com.zlobniy.domain.export.ExportAnswerView;
import com.zlobniy.domain.implementation.phone.PhoneImplService;
import com.zlobniy.domain.implementation.view.IncomingCall;
import com.zlobniy.domain.implementation.view.PhoneImplRequest;
import com.zlobniy.domain.panel.entity.Panel;
import com.zlobniy.domain.panel.service.PanelService;
import com.zlobniy.domain.survey.entity.Survey;
import com.zlobniy.domain.survey.service.SurveyService;
import com.zlobniy.domain.survey.view.PhoneSurveyView;
import com.zlobniy.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
public class PhoneController {

    private static final Logger LOG = LoggerFactory.getLogger( PhoneController.class );

    private ClientService clientService;
    private PanelService panelService;
    private SurveyService surveyService;
    private PhoneImplService phoneService;

    @Autowired
    public PhoneController( ClientService clientService,
                            PanelService panelService,
                            SurveyService surveyService,
                            PhoneImplService phoneService ) {
        this.clientService = clientService;
        this.panelService = panelService;
        this.surveyService = surveyService;
        this.phoneService = phoneService;
    }

    @RequestMapping( value = "/api/startSurvey", method = RequestMethod.POST )
    public void startSurvey( @RequestBody PhoneImplRequest view, HttpServletRequest request ) {

        String baseUrl = HttpUtils.getRootUrl( request );

        Client client = clientService.find( view.getClientId() );
        Panel panel = panelService.findFull( view.getPanelId() );
        Survey survey = surveyService.find( view.getSurveyId() );

        String sid = client.getProperty().getSid();
        String token = client.getProperty().getToken();
        String twilioPhone = client.getProperty().getPhoneNumber();
        String myPhone = panel.getData().get( 0 ).getValues().get( 0 ).getValue();

        phoneService.resetSurvey( view.getSurveyId() );

        Twilio.init( sid, token );

        try{

            Call.creator(
                    new com.twilio.type.PhoneNumber( myPhone ),
                    new com.twilio.type.PhoneNumber( twilioPhone ),
                    URI.create( baseUrl + "interview?surveyId=" + survey.getId() )
            ).create();

        }catch ( RuntimeException e ){

            LOG.error( e.getMessage(), e );
        }

    }

    @RequestMapping( value = "/interview", method = RequestMethod.POST )
    public void runSurveyInterviewPOST( HttpServletRequest request, HttpServletResponse response )
            throws TwiMLException, IOException {

        String surveyIdParameter = request.getParameter( "surveyId" );
        if( surveyIdParameter == null || surveyIdParameter.isEmpty() ) return;

        Long surveyId = Long.parseLong( surveyIdParameter );
        String to = request.getParameter( "To" );

        Survey survey = surveyService.find( surveyId );

        PhoneSurveyView surveyView = new PhoneSurveyView( survey );
        surveyView.setPhone( to );

        phoneService.addSurvey( surveyId, surveyView );

        IncomingCall call = getIncomingCall( request );

        String xml = phoneService.phoneHandler( call, surveyId );

        System.out.println( xml );

        response.getWriter().print( xml );
        response.setContentType( "application/xml" );
    }

    /**
     * Convert voice to text. Twilio function
     * */
    @RequestMapping( value = "/interview/{surveyId}/transcribe/{question}/{userId}", method = RequestMethod.POST )
    public String transcribeAnswer( @PathVariable Long surveyId,
                                    @PathVariable String question,
                                    @PathVariable String userId,
                                    HttpServletRequest request,
                                    HttpServletResponse response ) {

        IncomingCall call = getIncomingCall( request );
        // Get the phone and question numbers from the URL parameters provided by the "Record" verb

        int questionId = Integer.parseInt( question );
        // update answers in database, add speech to text.
        phoneService.addAnswerData( surveyId, questionId, userId, call.getTranscriptionText(), call.getRecordingUrl() );

        return "OK";
    }

    @RequestMapping( value = "/api/results/{id}", method = RequestMethod.GET )
    public List<ExportAnswerView> getResults( @PathVariable Long id ) {

        return phoneService.getAnswers( id );
    }


    @RequestMapping( value = "/readSms", method = RequestMethod.POST )
    public String readSms( @RequestBody PhoneRequest phoneRequest ) {

        Twilio.init( phoneRequest.getSid(), phoneRequest.getToken() );

        ResourceSet<Message> messages = Message.reader( phoneRequest.getSid() ).read();

        for ( Message message : messages ) {
            System.out.println( message );
            if ( message.getDirection().equals( Message.Direction.INBOUND ) ) {
                System.out.println( "incoming message: " + message.getBody() );
            }
        }

        return "get sms";

    }

    @RequestMapping( value = "/sendSms", method = RequestMethod.POST )
    public String sendSms( @RequestBody PhoneRequest phoneRequest, HttpServletRequest request ) {

        Twilio.init( phoneRequest.getSid(), phoneRequest.getToken() );

        Message message = Message
                .creator( new PhoneNumber( phoneRequest.getTo() ),
                        new PhoneNumber( phoneRequest.getFrom() ),
                        phoneRequest.getMessage() )
                .create();

        System.out.println( message.getSid() );

        return "send sms";

    }

    private IncomingCall getIncomingCall( HttpServletRequest request ) {
        return new IncomingCall(
                request.getParameter( "From" ),
                request.getParameter( "RecordingUrl" ),
                request.getParameter( "Digits" ),
                request.getParameter( "TranscriptionText" )
        );
    }

}
