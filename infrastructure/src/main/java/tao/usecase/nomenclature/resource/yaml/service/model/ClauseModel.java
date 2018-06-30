package tao.usecase.nomenclature.resource.yaml.service.model;

import lombok.Getter;
import tao.usecase.nomenclature.core.model.Clause;

import java.util.List;

@Getter
class ClauseModel {
    private String name;
    private List<String> values;

    Clause toDomain() {
        return Clause.builder()
                .name(name)
                .values(values)
                .build();
    }
}
