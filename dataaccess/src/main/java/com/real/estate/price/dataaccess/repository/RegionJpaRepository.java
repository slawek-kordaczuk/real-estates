package com.real.estate.price.dataaccess.repository;

import com.real.estate.price.dataaccess.entity.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RegionJpaRepository extends JpaRepository<RegionEntity, UUID> {

    Optional<RegionEntity> findByRegionCode(String regionCode);
}
