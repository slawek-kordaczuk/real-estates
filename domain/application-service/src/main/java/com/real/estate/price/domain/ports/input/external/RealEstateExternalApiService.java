package com.real.estate.price.domain.ports.input.external;

import com.real.estate.price.domain.dto.upsert.UpsertEstateCommand;
import com.real.estate.price.domain.dto.upsert.UpsertEstateResponse;
import com.real.estate.price.domain.entity.Region;
import jakarta.validation.Valid;

import java.util.List;

public interface RealEstateExternalApiService {

    UpsertEstateResponse upsertEstates(@Valid List<UpsertEstateCommand> upsertEstateCommands);

    List<Region> fetchAllRegions();

}
