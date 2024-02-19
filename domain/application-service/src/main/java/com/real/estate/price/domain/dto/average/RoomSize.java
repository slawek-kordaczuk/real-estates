package com.real.estate.price.domain.dto.average;

import lombok.Getter;

@Getter
public enum RoomSize {
    S(18,45), M(46, 80), L(81, 400);

    private final Integer sinceSize;
    private final Integer untilSize;

    RoomSize(Integer sinceSize, Integer untilSize) {
        this.sinceSize = sinceSize;
        this.untilSize = untilSize;
    }

}
