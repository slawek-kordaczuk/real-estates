package com.real.estate.price.external.api;

import com.real.estate.price.domain.entity.Region;
import com.real.estate.price.domain.ports.input.external.RealEstateExternalApiService;
import com.real.estate.price.external.exception.ExternalApiException;
import com.real.estate.price.external.mapper.PaymentEstateDataMapping;
import com.real.estate.price.external.model.RealEstatePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RealEstateExternalApiScheduler {

    private final RealEstateExternalApiService realEstateExternalApiService;
    private final RealEstateExternalApiClient realEstateExternalApiClient;

    private final PaymentEstateDataMapping paymentEstateDataMapping;

    public RealEstateExternalApiScheduler(RealEstateExternalApiService realEstateExternalApiService,
                                          RealEstateExternalApiClient realEstateExternalApiClient,
                                          PaymentEstateDataMapping paymentEstateDataMapping) {
        this.realEstateExternalApiService = realEstateExternalApiService;
        this.realEstateExternalApiClient = realEstateExternalApiClient;
        this.paymentEstateDataMapping = paymentEstateDataMapping;
    }

    @Scheduled(cron = "${external-api.cron}")
    public void fetchEstatesFromExternalApi() {
        List<Region> regions = realEstateExternalApiService.fetchAllRegions();
        regions.forEach(region -> {
            try {
                RealEstatePayload firstPage = realEstateExternalApiClient.getRegionPageEstates(region.getRegionType().getRegionCode(), "1");
                realEstateExternalApiService.upsertEstates(paymentEstateDataMapping.estatePayloadDataToUpsertCommands(firstPage.getData(), region));
                int totalPages = Integer.parseInt(firstPage.getTotalPages());
                for (int i = 2; i <= totalPages; i++) {
                    RealEstatePayload payload = realEstateExternalApiClient.getRegionPageEstates(region.getRegionType().getRegionCode(), String.valueOf(i));
                    realEstateExternalApiService.upsertEstates(paymentEstateDataMapping.estatePayloadDataToUpsertCommands(payload.getData(), region));
                }
            } catch (ExternalApiException e) {
                log.error("Can't get estates information for region {}", region.getRegionType().getRegionCode());
            }
        });
    }
}
