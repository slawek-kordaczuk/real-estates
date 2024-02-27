package com.real.estate.price.dataaccess.adapter;

import com.real.estate.price.dataaccess.entity.EstateEntity;
import com.real.estate.price.dataaccess.mapper.DataAccessMapper;
import com.real.estate.price.dataaccess.repository.EstateJpaRepository;
import com.real.estate.price.domain.dto.average.AverageEstateQuery;
import com.real.estate.price.domain.entity.Estate;
import com.real.estate.price.domain.exception.EstateDomainException;
import com.real.estate.price.domain.exception.FindEstateException;
import com.real.estate.price.domain.ports.output.repository.EstateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class EstateRepositoryImpl implements EstateRepository {

    private final EstateJpaRepository estateJpaRepository;

    private final DataAccessMapper dataAccessMapper;

    public EstateRepositoryImpl(EstateJpaRepository estateJpaRepository, DataAccessMapper dataAccessMapper) {
        this.estateJpaRepository = estateJpaRepository;
        this.dataAccessMapper = dataAccessMapper;
    }

    @Override
    public Estate save(Estate estate) {
        return dataAccessMapper.estateEntityToEstate(estateJpaRepository
                .save(dataAccessMapper.estateToEstateEntity(estate)));
    }

    @Override
    public List<Estate> findByQuery(AverageEstateQuery averageEstateQuery, Integer regionId) {
        List<EstateEntity> estates = estateJpaRepository.getEstatesByParams(regionId,
                averageEstateQuery.getRooms(),
                averageEstateQuery.getRoomSize().getSinceSize(),
                averageEstateQuery.getRoomSize().getUntilSize(),
                averageEstateQuery.getTypes(),
                averageEstateQuery.getDataSince(),
                averageEstateQuery.getDataUntil());
        if (estates.isEmpty()) {
            log.error("Could not find estates by providing parameters {}", averageEstateQuery);
            throw new FindEstateException("Could not find estates by providing parameters " + averageEstateQuery);
        }
        return estates
                .stream()
                .map(dataAccessMapper::estateEntityToEstate)
                .toList();
    }
}
