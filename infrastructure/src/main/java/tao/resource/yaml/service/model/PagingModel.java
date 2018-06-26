package tao.resource.yaml.service.model;

import lombok.Getter;
import tao.core.model.Paging;

@Getter
class PagingModel {
    private String enabled;
    private String packet;

    public Paging toDomainObject(){
        return Paging.builder()
                .enabled(enabled)
                .packet(packet)
                .build();
    }
}
