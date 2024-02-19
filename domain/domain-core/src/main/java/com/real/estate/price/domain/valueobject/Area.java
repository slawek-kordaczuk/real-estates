package com.real.estate.price.domain.valueobject;

import lombok.AllArgsConstructor;

import java.util.Objects;


public class Area {

    private Float squareMeter;

    public Area(Float squareMeter) {
        this.squareMeter = squareMeter;
    }

    public Float getSquareMeter() {
        return squareMeter;
    }

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
