package tao.core.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Clause {
    private String name;
    private String[] values;
}