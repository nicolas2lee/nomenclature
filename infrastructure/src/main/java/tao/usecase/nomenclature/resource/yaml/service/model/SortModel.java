package tao.usecase.nomenclature.resource.yaml.service.model;

import lombok.Getter;
import tao.usecase.nomenclature.core.model.Sort;

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
