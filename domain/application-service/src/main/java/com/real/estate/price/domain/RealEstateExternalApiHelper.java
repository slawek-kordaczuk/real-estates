package com.real.estate.price.domain;

import com.real.estate.price.domain.dto.upsert.UpsertEstateCommand;
import com.real.estate.price.domain.entity.Estate;
import com.real.estate.price.domain.entity.Region;
import com.real.estate.price.domain.exception.EstateCreateException;
import com.real.estate.price.domain.exception.EstateDomainException;
import com.real.estate.price.domain.exception.RegionFetchException;
import com.real.estate.price.domain.mapper.EstateDataMapper;
import com.real.estate.price.domain.ports.output.repository.EstateRepository;
import com.real.estate.price.domain.ports.output.repository.RegionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class RealEstateExternalApiHelper {

    private final EstateRepository estateRepository;

    private final RegionRepository regionRepository;

    private final RealEstateDomainService realEstateDomainService;

    private final EstateDataMapper estateDataMapper;

    public RealEstateExternalApiHelper(EstateRepository estateRepository,
                                       RegionRepository regionRepository,
                                       RealEstateDomainService realEstateDomainService,
                                       EstateDataMapper estateDataMapper) {
        this.estateRepository = estateRepository;
        this.regionRepository = regionRepository;
        this.realEstateDomainService = realEstateDomainService;
        this.estateDataMapper = estateDataMapper;
    }
    @Transactional
    public List<Estate> persistEstates(List<UpsertEstateCommand> upsertEstateCommands) {
        List<Estate> resultEstates = new ArrayList<>();
        upsertEstateCommands.forEach( command -> {
            Estate estate = estateDataMapper.upsertEstateCommandToEstate(command);
            realEstateDomainService.initializeAndValidateEstate(estate);
            try {
                resultEstates.add(saveEstate(estate));
            } catch (EstateCreateException e) {
                log.error("Could not save estate with id {}", estate.getId().getValue());
            }
        });
        return resultEstates;
    }

    public List<Region> fetchRegions(){
        return regionRepository.findAllRegions();
    }

    private Estate saveEstate(Estate estate) throws EstateCreateException {
        Estate estateResult = estateRepository.save(estate);
        if (estateResult == null) {
            throw new EstateCreateException("Could not save estate with id {} " + estate.getId().getValue());
        }
        log.info("Order is saved with id: {}", estateResult.getId().getValue());
        return estateResult;
    }

}
