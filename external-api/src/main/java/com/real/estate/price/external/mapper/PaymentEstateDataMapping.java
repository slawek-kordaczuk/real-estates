package com.real.estate.price.external.mapper;

import com.real.estate.price.domain.dto.upsert.UpsertEstateCommand;
import com.real.estate.price.domain.entity.Region;
import com.real.estate.price.domain.valueobject.EstateType;
import com.real.estate.price.domain.valueobject.RegionId;
import com.real.estate.price.external.model.RealEstateData;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class PaymentEstateDataMapping {

    public List<UpsertEstateCommand> estatePayloadDataToUpsertCommands(List<RealEstateData> realEstateData, Region region) {
        List<UpsertEstateCommand> upsertEstateCommands = new ArrayList<>();
        realEstateData.forEach(estate -> upsertEstateCommands.add(estatePayloadDataToUpsertCommand(estate, region)));
        return upsertEstateCommands;
    }

    public UpsertEstateCommand estatePayloadDataToUpsertCommand(RealEstateData realEstateData, Region region) {
        return UpsertEstateCommand.builder()
                .estateId(UUID.fromString(realEstateData.getId()))
                .region(region)
                .area(Float.valueOf(realEstateData.getArea()))
                .type(EstateType.valueOf(realEstateData.getType().toUpperCase()))
                .description(realEstateData.getDescription())
                .price(new BigDecimal(realEstateData.getPrice()))
                .rooms(Integer.valueOf(realEstateData.getRooms()))
                .build();
    }
}
