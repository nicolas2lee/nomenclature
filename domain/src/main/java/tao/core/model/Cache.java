package tao.core.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Cache {
    private Boolean enabled;
    private String expiration;
}
