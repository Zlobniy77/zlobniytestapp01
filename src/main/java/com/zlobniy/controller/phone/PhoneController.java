package com.zlobniy.controller.phone;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PhoneController {


    @RequestMapping( value = "/call", method = RequestMethod.GET )
    public String myCall( HttpServletRequest request ){



        return "call my baby";

    }

}
