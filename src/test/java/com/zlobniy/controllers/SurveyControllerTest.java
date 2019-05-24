package com.zlobniy.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zlobniy.domain.client.view.ClientView;
import com.zlobniy.domain.survey.view.SurveyView;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SurveyControllerTest {

    @Autowired
    private MockMvc mvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private TestRestTemplate template;

    @Ignore
    @WithMockUser(username="spring")
    @Test
    public void test() throws Exception {

        Long id = 1L;

        String token = obtainAccessToken( "ans", "123" );

        MvcResult result = mvc.perform(get("/api/survey/" + id )
                .header("Authorization", "Bearer " + token)
                )
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        SurveyView surveyView = objectMapper.readValue( result.getResponse().getContentAsString(), SurveyView.class );

        System.out.println("kk");

    }


    private String obtainAccessToken(String username, String password) throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", "fooClientIdPassword");
        params.add("username", username);
        params.add("password", password);

        ResultActions result
                = mvc.perform(post("/loggedIn")
                .params(params)
                .with(httpBasic("fooClientIdPassword","secret"))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        ClientView clientView = objectMapper.readValue( resultString, ClientView.class );

        return clientView.getToken();
    }

}
