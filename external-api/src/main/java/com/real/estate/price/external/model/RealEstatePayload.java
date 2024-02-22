package com.real.estate.price.external.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@AllArgsConstructor
//@NoArgsConstructor
//@Setter
@Builder
//@JsonDeserialize(builder = RealEstatePayload.RealEstatePayloadBuilder.class)
//@JsonSerialize
public class RealEstatePayload {
    @JsonProperty
    private String totalPages;
    @JsonProperty
    private List<RealEstateData> data;
}
