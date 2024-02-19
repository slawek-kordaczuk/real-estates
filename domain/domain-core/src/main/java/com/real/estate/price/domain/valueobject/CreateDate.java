package com.real.estate.price.domain.valueobject;

import java.time.LocalDateTime;

public class CreateDate {

    private LocalDateTime dateTime;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public CreateDate(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
