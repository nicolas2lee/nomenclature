package tao.core.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Paging {
    private String enabled;
    private String packet;
}
