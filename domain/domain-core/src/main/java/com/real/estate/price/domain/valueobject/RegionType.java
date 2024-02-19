package com.real.estate.price.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegionType {

    private final String regionCode;

    public String getRegionCode() {
        return regionCode;
    }
}
