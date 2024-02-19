package com.real.estate.price.domain.valueobject;

import lombok.AllArgsConstructor;

import java.util.UUID;

public class EstateId extends BaseId<UUID>{
    public EstateId(UUID value) {
        super(value);
    }
}
