package com.real.estate.price.domain.ports.output.repository;

import com.real.estate.price.domain.entity.Region;

import java.util.List;
import java.util.Optional;

public interface RegionRepository {

    List<Region> findAllRegions();
    Region findByCode(String code);
}
