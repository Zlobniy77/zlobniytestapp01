package com.zlobniy.domain.client.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PhoneProperty {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String sid;

    @Column
    private String appSid;

    @Column
    private String token;

    @Column
    private String phoneNumber;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Phone> phones = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid( String sid ) {
        this.sid = sid;
    }

    public String getAppSid() {
        return appSid;
    }

    public void setAppSid( String appSid ) {
        this.appSid = appSid;
    }

    public String getToken() {
        return token;
    }

    public void setToken( String token ) {
        this.token = token;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones( List<Phone> phones ) {
        this.phones = phones;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber( String phoneNumber ) {
        this.phoneNumber = phoneNumber;
    }
}
