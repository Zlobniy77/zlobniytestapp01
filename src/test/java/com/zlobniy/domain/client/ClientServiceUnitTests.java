//package com.zlobniy.domain.client;
//
//
//import com.zlobniy.domain.client.entity.Client;
//import com.zlobniy.domain.client.view.ClientView;
//import com.zlobniy.domain.client.view.RegistrationView;
//import org.junit.Assert;
//import org.junit.Test;
//
//import java.util.Objects;
//
//public class ClientServiceUnitTests {
//
//
//
//
//    /**
//     * This test check number of declared fields in both classes for view and for entity.
//     * If we add some fields we also need change tests, this test will fail if we forget about it.
//     * */
//    @Test
//    public void clientViewAndEntityNumberOfField(){
//        int expectedFieldClientView = 7;
//        int expectedFieldClient = 6;
//
//        int declaredFieldsClientView = ClientView.class.getDeclaredFields().length;
//        int declaredFieldsClient = Client.class.getDeclaredFields().length;
//
//        Assert.assertEquals( "Amount of fields in clientView was changed",
//                expectedFieldClientView, declaredFieldsClientView );
//        Assert.assertEquals( "Amount of fields in client was changed",
//                expectedFieldClient, declaredFieldsClient );
//    }
//
//    @Test
//    public void registrationFormViewNumberOfField(){
//        int expectedFieldRegistratioView = 3;
//
//        int declaredFieldsRegistrationView = RegistrationView.class.getDeclaredFields().length;
//
//        Assert.assertEquals( "Amount of fields in registration view was changed",
//                expectedFieldRegistratioView, declaredFieldsRegistrationView );
//    }
//
//
//    @Test
//    public void clientMappingTest(){
//
//        ClientView clientView = new ClientView( );
//        clientView.setEmail( "email" );
//        clientView.setPassword( "password" );
//        clientView.setToken( "token" );
//        clientView.setUsername( "username" );
//        clientView.setId( 1L );
//
//        Client client = new Client( clientView );
//
//        // test mapping
//        Assert.assertEquals( "email not mapped", clientView.getEmail(), client.getEmail() );
//        Assert.assertEquals( "password not mapped", clientView.getPassword(), client.getPassword() );
//        Assert.assertEquals( "token not mapped", clientView.getToken(), client.getToken() );
//        Assert.assertEquals( "username not mapped", clientView.getUsername(), client.getUsername() );
//        Assert.assertTrue( "id not mapped", Objects.equals( clientView.getId(), client.getId() ) );
//
//    }
//
//    @Test
//    public void clientMappingFromEntityToViewTest(){
//
//        Client client = new Client( );
//        client.setEmail( "email" );
//        client.setPassword( "password" );
//        client.setToken( "token" );
//        client.setUsername( "username" );
//        client.setId( 1L );
//
//        ClientView clientView = new ClientView( client );
//
//        // test mapping
//        Assert.assertEquals( "email not mapped", client.getEmail(), clientView.getEmail() );
//        Assert.assertEquals( "password not mapped", client.getPassword(), clientView.getPassword() );
//        Assert.assertEquals( "token not mapped", client.getToken(), clientView.getToken() );
//        Assert.assertEquals( "username not mapped", client.getUsername(), clientView.getUsername() );
//        Assert.assertTrue( "id not mapped", Objects.equals( client.getId(), clientView.getId() ) );
//
//    }
//
//    @Test
//    public void createClientFromRegistrationView(){
//
//        RegistrationView view = new RegistrationView();
//        view.setEmail( "testEmail@mail.ru" );
//        view.setPassword( "password" );
//        view.setUsername( "userName" );
//
//        Client client = new Client( view );
//
//        Assert.assertNotNull( "Client is null", client );
//        Assert.assertNotNull( "email is not mapped", client.getEmail() );
//        Assert.assertNotNull( "password is not mapped", client.getPassword() );
//        Assert.assertNotNull( "username in not mapped", client.getUsername() );
//
//    }
//
//}
