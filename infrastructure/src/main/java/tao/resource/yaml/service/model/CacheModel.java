package tao.resource.yaml.service.model;

import lombok.Getter;
import tao.core.model.Cache;

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
