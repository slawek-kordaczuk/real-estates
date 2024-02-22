package com.real.estate.price.external.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@SpringBootApplication
public class RealEstateExternalApiTestConfig {

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public WireMockServer webServer() {
        return new WireMockServer(options().dynamicPort());
    }

    @Bean
    public EmailService emailService() {
        return Mockito.mock(EmailService.class);
    }

    @Bean
    public RealEstateExternalApiScheduler realEstateExternalApiScheduler() {
        return Mockito.mock(RealEstateExternalApiScheduler.class);
    }

    @Bean
    public WebClient webClient(WireMockServer server){
        server.start();
        return WebClient.builder().baseUrl(server.baseUrl()).build();
    }

    @Bean
    public RealEstateExternalApiClient realEstateServiceClient(WebClient webClient, EmailService emailService) {
//        server.start();
//        WebClient webClient = WebClient.builder().baseUrl(server.baseUrl()).build();
        return new RealEstateExternalApiClient(webClient, emailService, objectMapper);
    }
}
