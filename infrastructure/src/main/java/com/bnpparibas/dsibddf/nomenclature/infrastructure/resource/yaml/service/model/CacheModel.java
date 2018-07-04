package com.bnpparibas.dsibddf.nomenclature.infrastructure.resource.yaml.service.model;

import lombok.Getter;
import com.bnpparibas.dsibddf.nomenclature.domain.core.model.Cache;

@Getter
class CacheModel {
    private String enabled;
    private String expiration;

    Cache toDomain() {
        return Cache.builder()
                .enabled(enabled.equals("1"))
                .expiration(expiration)
                .build();
    }
}
