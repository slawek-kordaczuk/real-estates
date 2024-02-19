package com.real.estate.price.external.api;


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
public class RealEstateServiceClient {

    private final WebClient webClient;

    private final EmailService emailService;

    @Value("${external-api.number-of-retries}")
    private int numberOfRetries;

    public RealEstateServiceClient(WebClient webClient, EmailService emailService) {
        this.webClient = webClient;
        this.emailService = emailService;
    }

    public RealEstatePayload getRegionPageEstates(String regionCode, String page) throws ExternalApiException {
        RealEstatePayload payload =
                webClient
                        .get()
                        .uri("/api/real-estates/" + regionCode + "?page=" + page)
                        .retrieve()
                        .bodyToMono(RealEstatePayload.class)
                        .retryWhen(Retry.backoff(numberOfRetries, Duration.ofSeconds(2))
                                .onRetryExhaustedThrow((spec, signal) -> {
                                    emailService.sendEmail(signal.totalRetries());
                                    throw new ExternalApiException(
                                            "Service call failed even after retrying " + signal.totalRetries() + " times");
                                }))
                        .block();

        if (payload == null) {
            throw new ExternalApiException("No response body was returned from the service");
        }
        return payload;
    }
}
