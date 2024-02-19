package com.real.estate.price.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CreateDate {

    private final LocalDateTime dateTime;
}
