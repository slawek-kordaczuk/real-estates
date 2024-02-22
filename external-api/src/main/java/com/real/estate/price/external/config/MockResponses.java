package com.real.estate.price.external.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import wiremock.org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Component
public class MockResponses {

    void stubRegionResponses(WireMockServer server) throws Exception {
        for (RegionCode code : RegionCode.values()) {
            server.stubFor(
                    get(urlPathMatching("/api/real-estates/" + code.name() + "?(.*)")
                    )
                            .willReturn(
                                    aResponse()
                                            .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                            .withBody(mockResponse(code.name() + ".json"))));
        }
    }

    private String mockResponse(String responseFileName) throws Exception {
        String responseFilePath = "mock_responses/" + responseFileName;
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(responseFilePath)).getFile());
        return FileUtils.readFileToString(
                file,
                StandardCharsets.UTF_8);
    }
}
