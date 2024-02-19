package com.real.estate.price.domain.mapper;

import com.real.estate.price.domain.dto.upsert.UpsertEstateCommand;
import com.real.estate.price.domain.entity.Estate;
import com.real.estate.price.domain.valueobject.Area;
import com.real.estate.price.domain.valueobject.EstateId;
import com.real.estate.price.domain.valueobject.Money;
import com.real.estate.price.domain.valueobject.RegionId;
import org.springframework.stereotype.Component;

@Component
public class EstateDataMapper {

    public Estate upsertEstateCommandToEstate(UpsertEstateCommand upsertEstateCommand){
        return Estate.builder()
                .estateId(new EstateId(upsertEstateCommand.getEstateId()))
                .region(upsertEstateCommand.getRegion())
                .area(new Area(upsertEstateCommand.getArea()))
                .type(upsertEstateCommand.getType())
                .description(upsertEstateCommand.getDescription())
                .rooms(upsertEstateCommand.getRooms())
                .price(new Money(upsertEstateCommand.getPrice()))
                .build();
    }
}
