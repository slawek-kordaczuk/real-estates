package com.real.estate.price.dataaccess.adapter;

import com.real.estate.price.dataaccess.entity.RegionEntity;
import com.real.estate.price.dataaccess.mapper.DataAccessMapper;
import com.real.estate.price.dataaccess.repository.RegionJpaRepository;
import com.real.estate.price.domain.entity.Region;
import com.real.estate.price.dataaccess.exception.RegionFetchException;
import com.real.estate.price.domain.ports.output.repository.RegionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j
@Component
public class RegionRepositoryImpl implements RegionRepository {

    private final RegionJpaRepository regionJpaRepository;

    private final DataAccessMapper dataAccessMapper;

    public RegionRepositoryImpl(RegionJpaRepository regionJpaRepository,
                                DataAccessMapper dataAccessMapper) {
        this.regionJpaRepository = regionJpaRepository;
        this.dataAccessMapper = dataAccessMapper;
    }

    @Override
    public List<Region> findAllRegions() {
        List<RegionEntity> regionEntities = regionJpaRepository.findAll();
        if (regionEntities.isEmpty()) {
            log.error("Could not find regions");
            throw new RegionFetchException("Could not find regions");
        }
        List<Region> regions = new ArrayList<>();
        regionEntities.forEach(entity -> regions.add(dataAccessMapper.regionEntityToRegion(entity)));
        return regions;
    }

    @Override
    public Region findByCode(String code) {
        Optional<RegionEntity> regionEntity = regionJpaRepository.findByRegionCode(code);
        if (regionEntity.isEmpty()) {
            log.error("Could not find region by code {}", code);
            throw new RegionFetchException("Could not find region by code " + code);
        }
        return dataAccessMapper.regionEntityToRegion(regionEntity.get());
    }
}
