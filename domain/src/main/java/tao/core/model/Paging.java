package tao.core.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Paging {
    @Getter(AccessLevel.NONE)
    private Boolean enabled;
    private String packet;

    public Boolean isEnabled() {
        return enabled;
    }
}
