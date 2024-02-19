package com.real.estate.price.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;


@Getter
@AllArgsConstructor
public class Area {

    private final Float squareMeter;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Area area = (Area) o;
        return Objects.equals(squareMeter, area.squareMeter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(squareMeter);
    }
}
