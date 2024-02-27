package com.real.estate.price.dataaccess.repository;

import com.real.estate.price.dataaccess.entity.EstateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EstateJpaRepository extends JpaRepository<EstateEntity, UUID> {

    @Query("SELECT estate FROM EstateEntity estate WHERE estate.region.id = :regionId AND estate.type IN :types AND estate.area >= :sizeSince AND estate.area <= :sizeUntil AND estate.createdDate >= :dateSince AND estate.createdDate <= :dateUntil AND estate.rooms = :rooms")
    List<EstateEntity> getEstatesByParams(@Param("regionId") Integer regionId,
                                                    @Param("rooms") Integer rooms,
                                                    @Param("sizeSince") Integer sizeSince,
                                                    @Param("sizeUntil") Integer sizeUntil,
                                                    @Param("types") List<String> types,
                                                    @Param("dateSince") LocalDate fromDate,
                                                    @Param("dateUntil") LocalDate toDate);
}
