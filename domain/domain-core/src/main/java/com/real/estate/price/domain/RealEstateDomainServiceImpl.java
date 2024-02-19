package com.real.estate.price.domain;

import com.real.estate.price.domain.entity.Estate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RealEstateDomainServiceImpl implements RealEstateDomainService {

    @Override
    public void initializeAndValidateEstate(Estate estate) {
        estate.initializeEstate();
        estate.validateEstate();
        log.info("Estate with id: {} is initiated", estate.getId().getValue());
    }
}
