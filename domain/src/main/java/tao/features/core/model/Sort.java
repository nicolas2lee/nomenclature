package tao.features.core.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Sort {
    private Boolean enabled;
    private List<String> fields;
    private List<String> direction;
}

