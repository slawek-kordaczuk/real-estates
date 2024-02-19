package com.real.estate.price.external.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RealEstatePayload {
    private String totalPages;
    private List<RealEstateData> data;
}
