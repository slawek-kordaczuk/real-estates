package com.real.estate.price.external.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RealEstateData {
    private String id;
    private String type;
    private String price;
    private String description;
    private String area;
    private String rooms;
}
