package com.real.estate.price.domain.ports.output.repository;

import com.real.estate.price.domain.dto.average.AverageEstateQuery;
import com.real.estate.price.domain.entity.Estate;

import java.util.List;

public interface EstateRepository {

    Estate save(Estate estate);

    List<Estate> findByQuery(AverageEstateQuery averageEstateQuery, Integer value);
}
