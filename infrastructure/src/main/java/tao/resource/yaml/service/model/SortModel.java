package tao.resource.yaml.service.model;

import lombok.Getter;

import java.util.List;

@Getter
class SortModel {
    private String enabled;
    private List<String> fields;
    private List<String> sens;
}
