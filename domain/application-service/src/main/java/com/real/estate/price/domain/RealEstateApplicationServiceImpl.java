package com.real.estate.price.domain;

import com.real.estate.price.domain.dto.average.AverageEstateQuery;
import com.real.estate.price.domain.dto.average.AverageEstateResponse;
import com.real.estate.price.domain.entity.Estate;
import com.real.estate.price.domain.entity.Region;
import com.real.estate.price.domain.ports.input.service.RealEstateApplicationService;
import com.real.estate.price.domain.ports.output.repository.EstateRepository;
import com.real.estate.price.domain.ports.output.repository.RegionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Slf4j
@Validated
@Component
public class RealEstateApplicationServiceImpl implements RealEstateApplicationService {

    private final EstateRepository estateRepository;

    private final RegionRepository regionRepository;

    public RealEstateApplicationServiceImpl(EstateRepository estateRepository, RegionRepository regionRepository) {
        this.estateRepository = estateRepository;
        this.regionRepository = regionRepository;
    }

    @Override
    public AverageEstateResponse calculateAveragePrice(AverageEstateQuery averageEstateQuery) {
        Region region = getRegionByCode(averageEstateQuery.getRegionCode());
        List<Estate> estates = estateRepository.findByQuery(averageEstateQuery, region.getId().getValue());
        if (estates.isEmpty()) {
            return AverageEstateResponse.builder()
                    .avgValue("0")
                    .build();
        }
        BigDecimal sum = BigDecimal.ZERO;
        for (Estate estate : estates) {
            sum = sum.add(estate.getPrice().getAmount());
        }
        BigDecimal averagePrice = sum.divide(BigDecimal.valueOf(estates.size()), RoundingMode.HALF_EVEN)
                .setScale(2, RoundingMode.HALF_EVEN);
        return AverageEstateResponse.builder()
                .avgValue(averagePrice.toString())
                .build();
    }

    private Region getRegionByCode(String regionCode) {
        return regionRepository.findByCode(regionCode);
    }
}
