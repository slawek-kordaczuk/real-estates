package com.real.estate.price.domain;

import com.real.estate.price.domain.dto.upsert.UpsertEstateCommand;
import com.real.estate.price.domain.dto.upsert.UpsertEstateResponse;
import com.real.estate.price.domain.entity.Estate;
import com.real.estate.price.domain.entity.Region;
import com.real.estate.price.domain.ports.input.external.RealEstateExternalApiService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RealEstateExternalApiServiceImpl implements RealEstateExternalApiService {

    private final RealEstateExternalApiHelper realEstateExternalApiHelper;

    public RealEstateExternalApiServiceImpl(RealEstateExternalApiHelper realEstateExternalApiHelper) {
        this.realEstateExternalApiHelper = realEstateExternalApiHelper;
    }

    @Override
    public UpsertEstateResponse upsertEstates(List<UpsertEstateCommand> upsertEstateCommands) {
        List<Estate> savedEstates = realEstateExternalApiHelper.persistEstates(upsertEstateCommands);
        return new UpsertEstateResponse(savedEstates.size());
    }

    @Override
    public List<Region> fetchAllRegions() {
        return realEstateExternalApiHelper.fetchRegions();
    }
}
