package com.zlobniy.controller.phone;


import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
public class PhoneController {


    @RequestMapping( value = "/startSurvey", method = RequestMethod.GET )
    public String collectGather1( HttpServletRequest request ){

        String sid = "ACbxx";
        String token = "41xx";
        String twilioPhone = "+1x";
        String myPhone = "+79xx";

        Twilio.init( sid, token );
        Call call = Call.creator(
                new com.twilio.type.PhoneNumber( myPhone ),
                new com.twilio.type.PhoneNumber( twilioPhone ),

                URI.create("http://e9704d68.ngrok.io/interview"))
                .create();

        System.out.println( call.getAccountSid() );

        return "start survey";

    }

    @RequestMapping( value = "/collectGather", method = RequestMethod.POST )
    public String collectGather2( HttpServletRequest request ){

        System.out.println( request );

        return "call my baby";

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
