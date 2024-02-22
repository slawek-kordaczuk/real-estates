package com.real.estate.price.external.api;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.real.estate.price.external.model.RealEstatePayload;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import wiremock.org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = RealEstateExternalApiTestConfig.class,
        properties = {
                "spring.mail.username=username",
                "spring.mail.receiver=receiver",
                "external-api.number-of-retries=1"
        })
public class RealEstateExternalApiClientTest {

    @Autowired
    private RealEstateExternalApiClient realEstateExternalApiClient;

    @Autowired
    private WireMockServer mockWebServer;

    @Test
    void testExternalApiClient() throws Exception {
        // Given
        mockWebServer.stubFor(
                get(urlEqualTo("/api/real-estates/TEST_REGION?page=1")
                )
                        .willReturn(
                                aResponse()
                                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                        .withBody(mockResponse())));

        // When
        RealEstatePayload result = realEstateExternalApiClient.getRegionPageEstates("TEST_REGION", "1");

        // Then
        assertEquals("99", result.getTotalPages());
    }

    private String mockResponse() throws Exception {
        String responseFilePath = "mock_responses/TEST_REGION.json";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(responseFilePath)).getFile());
        return FileUtils.readFileToString(
                file,
                StandardCharsets.UTF_8);
    }
}
