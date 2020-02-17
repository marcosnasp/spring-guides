package com.example.gsactuatorservice.controller;

import com.example.gsactuatorservice.GsActuatorServiceApplication;
import com.example.gsactuatorservice.client.GreetingService;
import com.example.gsactuatorservice.domain.Greeting;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest({GreetingService.class, GsActuatorServiceApplication.class})
public class HelloWorldControllerTest {

    @Autowired
    private GreetingService greetingServiceClient;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper objectMapper;

    public void createRequestGreeting(String nome) throws Exception {
        String respostaRequisicao = objectMapper.writeValueAsString(new Greeting(1, String.format("Hello, %s!", nome)));

        this.server
                .expect(requestTo(String.format("/hello-world?name=%s", nome)))
                .andRespond(withSuccess(respostaRequisicao, MediaType.APPLICATION_JSON));
    }

    @Test
    public void greetingToMarcos() throws Exception {
        Optional<String> name = Optional.of("Marcos");
        createRequestGreeting(name.get());
        Greeting greeting = this.greetingServiceClient.getGreeeting(name);

        assertThat(greeting.getId()).isEqualTo(1);
        assertThat(greeting.getContent()).isEqualTo("Hello, Marcos!");
    }

}
