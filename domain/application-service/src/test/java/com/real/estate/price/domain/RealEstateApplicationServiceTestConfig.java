package com.real.estate.price.domain;

import com.real.estate.price.domain.ports.output.repository.EstateRepository;
import com.real.estate.price.domain.ports.output.repository.RegionRepository;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RealEstateApplicationServiceTestConfig {

    @Bean
    public EstateRepository estateRepository() {
        return Mockito.mock(EstateRepository.class);
    }

    @Bean
    public RegionRepository regionRepository() {
        return Mockito.mock(RegionRepository.class);
    }

    @Bean
    public RealEstateDomainService realEstateDomainService() {
        return new RealEstateDomainServiceImpl();
    }

    static {
        System.setProperty("spring.config.location", "classpath:test.properties");
    }
}
