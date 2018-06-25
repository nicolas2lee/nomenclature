package tao.resource.yaml.service.entity;

import lombok.Getter;

import java.util.List;

@Getter
class SortEntity {
    private String enabled;
    private List<String> fields;
    private List<String> sens;
}
