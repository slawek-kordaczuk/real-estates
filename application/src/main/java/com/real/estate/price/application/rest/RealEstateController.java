package com.real.estate.price.application.rest;

import com.real.estate.price.domain.dto.average.AverageEstateQuery;
import com.real.estate.price.domain.dto.average.AverageEstateResponse;
import com.real.estate.price.domain.dto.average.RoomSize;
import com.real.estate.price.domain.ports.input.service.RealEstateApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RealEstateController {

    private final RealEstateApplicationService realEstateApplicationService;

    public RealEstateController(RealEstateApplicationService realEstateApplicationService) {
        this.realEstateApplicationService = realEstateApplicationService;
    }

    @GetMapping("/real-estates-stats/{regionCode}")
    public ResponseEntity<AverageEstateResponse> getOrderByTrackingId(@PathVariable String regionCode,
                                                                      @RequestParam String size,
                                                                      @RequestParam String rooms,
                                                                      @RequestParam List<String> types,
                                                                      @RequestParam @DateTimeFormat(pattern = "yyyyMMdd") Date dateSince,
                                                                      @RequestParam @DateTimeFormat(pattern = "yyyyMMdd") Date dateUntil) {
        AverageEstateResponse averageEstateResponse =
                realEstateApplicationService.calculateAveragePrice(AverageEstateQuery.builder()
                        .regionCode(regionCode)
                        .roomSize(RoomSize.valueOf(size))
                        .rooms(Integer.valueOf(rooms))
                        .types(typesToUpperCase(types))
                        .dataSince(convertToLocalDate(dateSince))
                        .dataUntil(convertToLocalDate(dateUntil))
                        .build());
        log.info("Returning average value: {}", averageEstateResponse.getAvgValue());
        return ResponseEntity.ok(averageEstateResponse);
    }

    private LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private List<String> typesToUpperCase(List<String> types) {
        return types.stream().map(String::toUpperCase).toList();
    }
}
