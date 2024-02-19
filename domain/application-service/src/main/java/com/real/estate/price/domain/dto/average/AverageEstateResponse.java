package com.real.estate.price.domain.dto.average;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@JsonDeserialize(builder = AverageEstateResponse.AverageEstateResponseBuilder.class)
public class AverageEstateResponse {
    @JsonProperty
    private String avgValue;
}
