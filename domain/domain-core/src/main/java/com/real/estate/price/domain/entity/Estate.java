package com.real.estate.price.domain.entity;

import com.real.estate.price.domain.exception.EstateDomainException;
import com.real.estate.price.domain.valueobject.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Estate extends Entity<EstateId>{

    private final Region region;
    private final EstateType type;
    private final Money price;
    private final String description;
    private final Area area;
    private final Integer rooms;
    private CreateDate createDate;

    public void initializeEstate() {
        this.createDate = new CreateDate(LocalDateTime.now());
    }

    public void validateEstate() {
        if(getId() == null || region == null) {
            throw new EstateDomainException("Estate is not correct for initialization");
        }
    }

    public Estate(Builder builder) {
        super.setId(builder.estateId);
        this.region = builder.region;
        this.type = builder.type;
        this.price = builder.price;
        this.description = builder.description;
        this.area = builder.area;
        this.rooms = builder.rooms;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private EstateId estateId;
        private Region region;
        private EstateType type;
        private Money price;
        private String description;
        private Area area;
        private Integer rooms;

        private Builder() {
        }

        public Builder estateId(EstateId val) {
            estateId = val;
            return this;
        }
        public Builder region(Region val) {
            region = val;
            return this;
        }
        public Builder type(EstateType val) {
            type = val;
            return this;
        }
        public Builder price(Money val) {
            price = val;
            return this;
        }
        public Builder description(String val) {
            description = val;
            return this;
        }
        public Builder area(Area val) {
            area = val;
            return this;
        }
        public Builder rooms(Integer val) {
            rooms = val;
            return this;
        }
        public Estate build() {
            return new Estate(this);
        }
    }
}
