package com.real.estate.price.domain;


import com.real.estate.price.domain.dto.average.AverageEstateQuery;
import com.real.estate.price.domain.dto.average.AverageEstateResponse;
import com.real.estate.price.domain.entity.Estate;
import com.real.estate.price.domain.entity.Region;
import com.real.estate.price.domain.ports.input.service.RealEstateApplicationService;
import com.real.estate.price.domain.ports.output.repository.EstateRepository;
import com.real.estate.price.domain.ports.output.repository.RegionRepository;
import com.real.estate.price.domain.valueobject.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.UUID;

import static com.real.estate.price.domain.dto.average.RoomSize.M;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = RealEstateApplicationServiceTestConfig.class)
public class RealEstateApplicationServiceTest {

    @Autowired
    private RealEstateApplicationService realEstateApplicationService;

    @Autowired
    private EstateRepository estateRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Test
    public void testAveragePrice() {
        //Given
        AverageEstateQuery averageEstateQuery = AverageEstateQuery.builder()
                .regionCode("SL_KATO")
                .rooms(3)
                .roomSize(M)
                .types(List.of("detached_house", "flat"))
                .dataSince(LocalDateTime.of(2024,
                        Month.FEBRUARY, 18, 19, 30, 40))
                .dataUntil(LocalDateTime.now())
                .build();
        Region region = Region.builder()
                .regionId(new RegionId(1))
                .description("test description")
                .regionType(new RegionType("SL_KATO"))
                .build();
        Estate estate = Estate.builder()
                .estateId(new EstateId(UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb41")))
                .region(region)
                .price(new Money(new BigDecimal(123456)))
                .area(new Area(Float.valueOf("12.5")))
                .type(EstateType.FLAT)
                .description("estate description")
                .rooms(3)
                .build();
        when(regionRepository.findByCode(any(String.class))).thenReturn(region);
        when(estateRepository.findByQuery(any(AverageEstateQuery.class), any(Integer.class))).thenReturn(List.of(estate));

        //When
        AverageEstateResponse averageEstateResponse = realEstateApplicationService.calculateAveragePrice(averageEstateQuery);

        //Then
        assertEquals(averageEstateResponse.getAvgValue(), "123456");
    }

}
