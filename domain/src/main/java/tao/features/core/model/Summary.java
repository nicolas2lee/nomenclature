package tao.features.core.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Summary {
    private Boolean enabled;
    private String nbElementsAttributeName;
    private String totalAttributeName;
}
