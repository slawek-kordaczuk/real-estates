package com.real.estate.price;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.real.estate.price.domain.RealEstateDomainService;
import com.real.estate.price.domain.RealEstateDomainServiceImpl;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@Configuration
public class RealEstateConfiguration {

    @Bean
    public RealEstateDomainService realEstateDomainService() {
        return new RealEstateDomainServiceImpl();
    }
}
