package com.real.estate.price.domain.dto.upsert;

import com.real.estate.price.domain.entity.Region;
import com.real.estate.price.domain.valueobject.EstateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class UpsertEstateCommand {
    private UUID estateId;
    private Region region;
    private EstateType type;
    private BigDecimal price;
    private String description;
    private Float area;
    private Integer rooms;
}
