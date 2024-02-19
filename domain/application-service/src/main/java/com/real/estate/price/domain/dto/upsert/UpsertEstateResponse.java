package com.real.estate.price.domain.dto.upsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UpsertEstateResponse {
    private final Integer numberOfCreatedEstates;
}
