package com.zlobniy.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainInterceptor extends HandlerInterceptorAdapter {

    private static Logger log = LoggerFactory.getLogger( MainInterceptor.class );

    public MainInterceptor() {

    }

    @Override
    public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler ) {
        return true;
    }

    @Override
    public void postHandle( HttpServletRequest request,
                            HttpServletResponse response,
                            Object handler,
                            ModelAndView modelAndView ) {

        // You can add attributes in the modelAndView
        // and use that in the view page
    }

    @Override
    public void afterCompletion( HttpServletRequest request,
                                 HttpServletResponse response,
                                 Object handler,
                                 Exception ex ) {

    }

}
