package com.bnpparibas.dsibddf.nomenclature.infrastructure.resource.yaml.service.model;

import lombok.Getter;
import com.bnpparibas.dsibddf.nomenclature.domain.core.model.Summary;

@Getter
class SummaryModel {
    private String enabled;
    private String nbElementsAttributeName;
    private String totalAttributeName;

    Summary toDomain() {
        return Summary.builder()
                .enabled(enabled.equals("1"))
                .nbElementsAttributeName(nbElementsAttributeName)
                .totalAttributeName(totalAttributeName)
                .build();
    }
}
