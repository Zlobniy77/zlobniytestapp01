package com.zlobniy.domain.client.entity;

import com.zlobniy.domain.client.view.ClientView;
import com.zlobniy.domain.client.view.RegistrationView;
import com.zlobniy.domain.folder.entity.Folder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Client {

    @Id
    @GeneratedValue
    private Long id;

    @Column( unique = true )
    private String username;

    @Column
    private String token;

    @Column
    private String password;

    @Column
    private String email;

    @OneToMany( mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    private List<Folder> folders = new ArrayList<>(  );

    @OneToOne( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private PhoneProperty property = new PhoneProperty();

    public Client() {

    }

    public Client( RegistrationView registrationView ) {
        setUsername( registrationView.getUsername() );
        setPassword( registrationView.getPassword() );
        setEmail( registrationView.getEmail() );
    }

    public Client( ClientView clientView ) {
        setUsername( clientView.getUsername() );
        setPassword( clientView.getPassword() );
        setEmail( clientView.getEmail() );
        setId( clientView.getId() );
        setToken( clientView.getToken() );
    }


    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername( String username ) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken( String token ) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    public List<Folder> getFolders() {
        return folders;
    }

    public void setFolders( List<Folder> folders ) {
        this.folders = folders;
    }

    public PhoneProperty getProperty() {
        return property;
    }

    public void setProperty( PhoneProperty property ) {
        this.property = property;
    }
}
