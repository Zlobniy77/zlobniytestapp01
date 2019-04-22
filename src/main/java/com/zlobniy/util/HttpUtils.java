package com.zlobniy.util;

import javax.servlet.http.HttpServletRequest;

public class HttpUtils {

    public static String getRootUrl( HttpServletRequest request ) {
        return String.format( "%s://%s:%d/", request.getScheme(), request.getServerName(), request.getServerPort() );
    }

}
