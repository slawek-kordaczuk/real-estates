package com.real.estate.price.domain.dto.average;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class AverageEstateQuery {

    @NotNull
    private String regionCode;
    private RoomSize roomSize;
    private List<String> types;
    private Integer rooms;
    private LocalDateTime dataSince;
    private LocalDateTime dataUntil;

}
