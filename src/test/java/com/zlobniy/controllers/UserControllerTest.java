package com.zlobniy.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zlobniy.domain.client.view.ClientView;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

    @LocalServerPort
    private int port;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {

        MvcResult result = this.mockMvc.perform(post("/loggedIn")
                .param( "username", "ans" ).param( "password", "123" ))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        ClientView clientView = objectMapper.readValue( result.getResponse().getContentAsString(), ClientView.class );

        Assert.assertTrue( "login not working", clientView != null );
        Assert.assertTrue( "token is empty", clientView.getToken().length() > 0 );

    }



//    @Test
//    public void testLoginEndpoint() {
//
//        //String data = this.restTemplate.getForObject("http://localhost:" + port + "/", String.class);
//        Object clientView = restTemplate.getForEntity( "http://localhost:8080/loggedIn", ClientView.class );
//
//        System.out.println( clientView );
//       //assertThat( data.contains("Hello World") );
//
//    }

}
