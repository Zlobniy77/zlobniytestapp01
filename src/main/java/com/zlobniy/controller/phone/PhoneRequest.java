package com.zlobniy.controller.phone;

public class PhoneRequest {

    private String sid;
    private String token;

    private String to;
    private String from;
    private String message;

    public String getSid() {
        return sid;
    }

    public void setSid( String sid ) {
        this.sid = sid;
    }

    public String getToken() {
        return token;
    }

    public void setToken( String token ) {
        this.token = token;
    }

    public String getTo() {
        return to;
    }

    public void setTo( String to ) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom( String from ) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }
}
