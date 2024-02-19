package com.real.estate.price;

import com.real.estate.price.domain.dto.average.AverageEstateResponse;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RealEstateApplicationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldReturnAveragePrice() {
        //Given & When
        AverageEstateResponse response = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/real-estates-stats/{regionCode}")
                        .queryParam("rooms", "3")
                        .queryParam("size", "M")
                        .queryParam("types", "detached_house,flat")
                        .queryParam("dateSince", "20210501")
                        .queryParam("dateUntil", "20210910")
                        .build("SL_KATO"))

                .exchange()
                .expectStatus()
                .isEqualTo(OK)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(AverageEstateResponse.class)
                .returnResult()
                .getResponseBody();

        //Then
        assertEquals(response.getAvgValue(), "0");
    }

}
