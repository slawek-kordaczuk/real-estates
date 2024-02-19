package com.real.estate.price.dataaccess.mapper;

import com.real.estate.price.dataaccess.entity.EstateEntity;
import com.real.estate.price.dataaccess.entity.RegionEntity;
import com.real.estate.price.domain.entity.Estate;
import com.real.estate.price.domain.entity.Region;
import com.real.estate.price.domain.valueobject.*;
import org.springframework.stereotype.Component;

@Component
public class DataAccessMapper {

    public EstateEntity estateToEstateEntity(Estate estate) {
        return EstateEntity.builder()
                .id(estate.getId().getValue())
                .region(regionToRegionEntity(estate.getRegion()))
                .type(estate.getType())
                .rooms(estate.getRooms())
                .area(estate.getArea().getSquareMeter())
                .price(estate.getPrice().getAmount())
                .createdDate(estate.getCreateDate().getDateTime())
                .description(estate.getDescription())
                .build();
    }

    public Estate estateEntityToEstate(EstateEntity estateEntity) {
        return Estate.builder()
                .estateId(new EstateId(estateEntity.getId()))
                .region(regionEntityToRegion(estateEntity.getRegion()))
                .type(estateEntity.getType())
                .area(new Area(estateEntity.getArea()))
                .rooms(estateEntity.getRooms())
                .description(estateEntity.getDescription())
                .price(new Money(estateEntity.getPrice()))
                .build();
    }

    public RegionEntity regionToRegionEntity(Region region) {
        return RegionEntity.builder()
                .id(region.getId().getValue())
                .regionCode(region.getRegionType().getRegionCode())
                .description(region.getDescription())
                .build();
    }

    public Region regionEntityToRegion(RegionEntity regionEntity) {
        return Region.builder()
                .regionId(new RegionId(regionEntity.getId()))
                .regionType(new RegionType(regionEntity.getRegionCode()))
                .description(regionEntity.getDescription())
                .build();
    }
}
