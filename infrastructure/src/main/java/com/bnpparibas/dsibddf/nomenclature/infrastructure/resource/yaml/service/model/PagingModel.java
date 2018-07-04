package com.bnpparibas.dsibddf.nomenclature.infrastructure.resource.yaml.service.model;

import lombok.Getter;
import com.bnpparibas.dsibddf.nomenclature.domain.core.model.Paging;

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
