package com.real.estate.price.domain.ports.input.service;

import com.real.estate.price.domain.dto.average.AverageEstateQuery;
import com.real.estate.price.domain.dto.average.AverageEstateResponse;
import jakarta.validation.Valid;

public interface RealEstateApplicationService {

    AverageEstateResponse calculateAveragePrice(@Valid AverageEstateQuery averageEstateQuery);
}
