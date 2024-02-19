package com.real.estate.price.domain.entity;

import com.real.estate.price.domain.valueobject.EstateId;
import com.real.estate.price.domain.valueobject.EstateType;
import com.real.estate.price.domain.valueobject.RegionId;
import com.real.estate.price.domain.valueobject.RegionType;

import java.util.List;

public class Region extends Entity<RegionId> {

    private RegionType regionType;
    private String description;

    public RegionType getRegionType() {
        return regionType;
    }

    public String getDescription() {
        return description;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Region(Builder builder) {
        super.setId(builder.regionId);
        this.regionType = builder.regionType;
        this.description = builder.description;
    }

    public static final class Builder {
        private RegionId regionId;
        private RegionType regionType;
        private String description;

        public Builder regionId(RegionId val) {
            regionId = val;
            return this;
        }

        public Builder regionType(RegionType val) {
            regionType = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Region build() {
            return new Region(this);
        }
    }
}
