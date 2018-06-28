package tao.features.core.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Summary {
    @Getter(AccessLevel.NONE)
    private Boolean enabled;
    private String nbElementsAttributeName;
    private String totalAttributeName;

    public Boolean isEnabled() {
        return enabled;
    }
}
