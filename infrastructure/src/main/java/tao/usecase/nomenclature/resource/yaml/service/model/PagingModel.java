package tao.usecase.nomenclature.resource.yaml.service.model;

import lombok.Getter;
import tao.usecase.nomenclature.core.model.Paging;

@Getter
class PagingModel {
    private String enabled;
    private String packet;

    public Paging toDomainObject(){
        return Paging.builder()
                .enabled(enabled.equals("1"))
                .packet(packet)
                .build();
    }
}
