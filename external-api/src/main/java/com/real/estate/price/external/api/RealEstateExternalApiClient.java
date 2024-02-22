package com.real.estate.price.external.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.real.estate.price.external.exception.ExternalApiException;
import com.real.estate.price.external.model.RealEstatePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;

@Slf4j
@Component
public class RealEstateExternalApiClient {

    private final WebClient webClient;

    private final EmailService emailService;

    private final ObjectMapper objectMapper;

    @Value("${external-api.number-of-retries}")
    private int numberOfRetries;

    public RealEstateExternalApiClient(WebClient webClient, EmailService emailService, ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.emailService = emailService;
        this.objectMapper = objectMapper;
    }

    public RealEstatePayload getRegionPageEstates(String regionCode, String page) throws ExternalApiException {
        String payload =
                webClient
                        .get()
                        .uri("/api/real-estates/" + regionCode + "?page=" + page)
                        .retrieve()
                        .bodyToMono(String.class)
                        .retryWhen(Retry.backoff(numberOfRetries, Duration.ofSeconds(2))
                                .onRetryExhaustedThrow((spec, signal) -> {
                                    emailService.sendEmail(signal.totalRetries());
                                    throw new ExternalApiException(
                                            "Service call failed after retrying " + signal.totalRetries() + " times");
                                }))
                        .block();

        if (payload == null) {
            throw new ExternalApiException("No response body was returned from the service");
        }
        return convertToRealEstatePayload(payload);
    }

    private RealEstatePayload convertToRealEstatePayload(String realEstateResponse) {
        try {
            return objectMapper.readValue(realEstateResponse, RealEstatePayload.class);
        } catch (JsonProcessingException e) {
            throw new ExternalApiException("Could not read " + RealEstatePayload.class.getName() + " object!");
        }
    }
}
