package com.bnpparibas.dsibddf.nomenclature.infrastructure.resource.yaml.service.model;

import lombok.Getter;
import com.bnpparibas.dsibddf.nomenclature.domain.core.model.Sort;

import java.util.List;

@Getter
class SortModel {
    private String enabled;
    private List<String> fields;
    private List<String> sens;

    public Sort toDomain() {
        return Sort.builder()
                .enabled(enabled.equals("1"))
                .fields(fields)
                .direction(sens)
                .build();
    }
}
