package com.real.estate.price.domain;

import com.real.estate.price.domain.dto.average.AverageEstateQuery;
import com.real.estate.price.domain.dto.upsert.UpsertEstateCommand;
import com.real.estate.price.domain.dto.upsert.UpsertEstateResponse;
import com.real.estate.price.domain.entity.Estate;
import com.real.estate.price.domain.entity.Region;
import com.real.estate.price.domain.ports.input.external.RealEstateExternalApiService;
import com.real.estate.price.domain.ports.output.repository.EstateRepository;
import com.real.estate.price.domain.ports.output.repository.RegionRepository;
import com.real.estate.price.domain.valueobject.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = RealEstateApplicationServiceTestConfig.class)
public class RealEstateExternalApiServiceTest {

    @Autowired
    private EstateRepository estateRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private RealEstateExternalApiService realEstateExternalApiService;

    @Test
    public void testCreateEstate(){
        //Given
        Region region = Region.builder()
                .regionId(new RegionId(1))
                .description("test description")
                .regionType(new RegionType("SL_KATO"))
                .build();
        Estate estate = Estate.builder()
                .estateId(new EstateId(UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb48")))
                .region(region)
                .price(new Money(new BigDecimal(123456)))
                .area(new Area(Float.valueOf("12.5")))
                .type(EstateType.FLAT)
                .description("estate description")
                .rooms(3)
                .build();
        UpsertEstateCommand upsertEstateCommand = UpsertEstateCommand.builder()
                .estateId(UUID.fromString("15a497c1-0f4b-4eff-b9f4-c402c8c07afb"))
                .rooms(3)
                .type(EstateType.FLAT)
                .area(Float.valueOf("12.4"))
                .description("test command description")
                .region(region)
                .price(new BigDecimal(123456))
                .build();

        when(estateRepository.save(any(Estate.class))).thenReturn(estate);

        //When
        UpsertEstateResponse upsertEstateResponse = realEstateExternalApiService.upsertEstates(List.of(upsertEstateCommand));

        //Then
        assertEquals(upsertEstateResponse.getNumberOfCreatedEstates(), 1);
    }

    @Test
    public void testFetchRegions(){
        //Given
        Region region = Region.builder()
                .regionId(new RegionId(1))
                .description("test description")
                .regionType(new RegionType("SL_KATO"))
                .build();

        when(regionRepository.findAllRegions()).thenReturn(List.of(region));

        //When
        List<Region> regions = realEstateExternalApiService.fetchAllRegions();

        //Then
        assertEquals(regions.size(), 1);
    }
}
