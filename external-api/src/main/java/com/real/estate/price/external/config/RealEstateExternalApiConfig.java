package com.real.estate.price.external.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;
import wiremock.org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@Slf4j
@Configuration
@EnableScheduling
public class RealEstateExternalApiConfig {

    private final MockResponses mockResponses;

    @Value("${spring.mail.username}")
    private String emailSender;

    @Value("${spring.mail.password}")
    private String emailPassword;

    public RealEstateExternalApiConfig(MockResponses mockResponses) {
        this.mockResponses = mockResponses;
    }

    @Bean
    public WireMockServer webServer() {
        return new WireMockServer(options().dynamicPort());
    }

    @Bean
    public WebClient webClient(WireMockServer server) throws Exception {
        mockResponses.stubRegionResponses(server);
        server.start();
        return WebClient.builder().baseUrl(server.baseUrl()).build();
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername(emailSender);
        mailSender.setPassword(emailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

}
