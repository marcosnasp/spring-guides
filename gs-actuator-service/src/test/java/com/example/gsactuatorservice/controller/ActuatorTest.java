package com.example.gsactuatorservice.controller;

import com.example.gsactuatorservice.GsActuatorServiceApplication;
import com.example.gsactuatorservice.client.GreetingService;
import com.example.gsactuatorservice.domain.Greeting;
import com.example.gsactuatorservice.domain.Status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest({GsActuatorServiceApplication.class, Status.class})
public class ActuatorTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MockRestServiceServer server;

    public void createRequestActuator() throws Exception {
        final String resposta = "{\n" +
                "  \"status\": \"UP\"\n" +
                "}";
        this.server
                .expect(requestTo("/actuator/health"))
                .andRespond(withSuccess(resposta, MediaType.APPLICATION_JSON));
    }
//GET http://localhost:8080/actuator/health
    @Test
    public void actuatorHealthShouldBeUp() throws Exception {
        createRequestActuator();

        Status status = restTemplate.getForObject("/actuator/health", Status.class);
        assertThat(status.getStatus()).isEqualTo("UP");
    }
}
