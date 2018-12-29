package com.zlobniy.controller.phone;


import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Gather;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PhoneController {


    @RequestMapping( value = "/call", method = RequestMethod.GET )
    public String myCall( HttpServletRequest request ){

        Gather gather = new Gather.Builder().inputs( Gather.Input.SPEECH ).build();

        VoiceResponse twimlResponse = new VoiceResponse.Builder().gather( gather ).build();

        return twimlResponse.toXml();

    }

}
