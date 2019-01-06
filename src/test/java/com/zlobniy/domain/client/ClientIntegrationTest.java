package com.zlobniy.domain.client;

import com.zlobniy.domain.client.entity.Client;
import com.zlobniy.domain.client.service.ClientService;
import com.zlobniy.domain.client.view.ClientView;
import com.zlobniy.domain.client.view.RegistrationView;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

/**
 * Integration tests with database.
 * */
@RunWith( SpringJUnit4ClassRunner.class )
@Transactional
@SpringBootTest
public class ClientIntegrationTest {


    @Autowired
    private ClientService clientService;


    @Test
    public void getSuperUserTest(){
        List<Client> clients = clientService.findAll();

        System.out.println( clients.size() );
        Assert.assertEquals( "Should be ONE user on start application", 1, clients.size() );

        Client superUser = clients.get( 0 );

        Assert.assertEquals( "Super user should be ans", "ans", superUser.getUsername() );

    }



    @Test
    public void saveClientTest(){

        ClientView clientView = new ClientView( );
        clientView.setEmail( "email" );
        clientView.setPassword( "password" );
        clientView.setToken( "token" );
        clientView.setUsername( "username" );

        Client client = new Client( clientView );

        Client savedClient = clientService.saveClient( client );

        Assert.assertTrue( "Should be id after save to database", savedClient.getId() > 0 );

        ClientView saveClientView = new ClientView( savedClient );

        // test mapping
        Assert.assertEquals( "email not mapped", savedClient.getEmail(), saveClientView.getEmail() );
        Assert.assertEquals( "password not mapped", savedClient.getPassword(), saveClientView.getPassword() );
        Assert.assertEquals( "token not mapped", savedClient.getToken(), saveClientView.getToken() );
        Assert.assertEquals( "username not mapped", savedClient.getUsername(), saveClientView.getUsername() );
        Assert.assertTrue( "id not mapped", Objects.equals( savedClient.getId(), saveClientView.getId() ) );

    }

    @Ignore
    @Test
    public void clientLoginLogoutTest(){

        Client notLoggedInClient = clientService.find( 1L );
        Assert.assertNull( "Token should be null", notLoggedInClient.getToken() );

        Client client = clientService.login( "ans", "123" );
        Assert.assertNotNull( "Find client by username and password", client );
        Assert.assertNotNull( "Should be token", client.getToken() );

        clientService.logout( 1L );

        Client findClientById = clientService.find( 1L );
        Assert.assertNotNull( "Find client by id", findClientById );
//        Assert.assertNull( "Token Should be null", findClientById.getToken() );

    }

    @Test
    public void clientLoginTestNegativeScenario(){
        Client client = clientService.login( "admin", "111" );
        Assert.assertNull( "Wrong credentials should return null", client );

    }

    @Test
    public void addNewClientTest(){

        RegistrationView view = new RegistrationView();
        view.setEmail( "testEmail@mail.ru" );
        view.setPassword( "password" );
        view.setUsername( "userName" );

        Client client = clientService.createClient( view );

        Assert.assertNotNull( "New client is null", client );
        Assert.assertNotNull( "id is null", client.getId() );
        Assert.assertNotNull( "Token should be not null", client.getToken() );

    }

    @Test
    public void tryToCreateClientWithExistedUserName(){

        RegistrationView view = new RegistrationView();
        view.setEmail( "ans@mail.ru" );
        view.setPassword( "password" );
        view.setUsername( "ans" );

        Client client = clientService.createClient( view );


        Assert.assertNull( "Create client with existed user name", client );

    }



}
