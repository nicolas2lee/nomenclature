package tao.resource.yaml.service.entity;

import lombok.Getter;
import tao.core.model.Paging;

@Getter
class PagingEntity {
    private String enabled;
    private String packet;

    public Paging toDomainObject(){
        return Paging.builder()
                .enabled(enabled)
                .packet(packet)
                .build();
    }
}
