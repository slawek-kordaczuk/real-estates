package com.real.estate.price.dataaccess.entity;

import com.real.estate.price.domain.valueobject.*;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "estate")
@Entity
public class EstateEntity {

    @Id
    private UUID id;
    @Enumerated(EnumType.STRING)
    private EstateType type;
    private BigDecimal price;
    private String description;
    private Float area;
    private Integer rooms;
    private LocalDateTime createdDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "REGION_ID")
    private RegionEntity region;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EstateEntity that = (EstateEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
