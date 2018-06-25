package tao.core.model;

import lombok.Getter;

import java.util.List;

@Getter
public class Sort {
    private String enabled;
    private List<String> fields;
    private List<String> direction;
}

