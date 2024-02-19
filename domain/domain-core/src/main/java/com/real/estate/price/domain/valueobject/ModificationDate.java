package com.real.estate.price.domain.valueobject;

import java.time.LocalDateTime;

public class ModificationDate {

    private LocalDateTime dateTime;

    public ModificationDate(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
