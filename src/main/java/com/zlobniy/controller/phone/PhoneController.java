package com.zlobniy.controller.phone;


import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Dial;
import com.twilio.twiml.voice.Number;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhoneController {


    @RequestMapping( value = "/call.xml", method = RequestMethod.GET )
    public String call(){

        Dial.Builder dialBuilder = new Dial.Builder();

        Number number = new Number.Builder( "+79216440112" ).build();
        dialBuilder = dialBuilder.number( number ).callerId( "+4592451871" );

        Dial dial = dialBuilder.build();

        VoiceResponse twimlResponse = new VoiceResponse.Builder().dial( dial ).build();

        return twimlResponse.toXml();

    }

}
