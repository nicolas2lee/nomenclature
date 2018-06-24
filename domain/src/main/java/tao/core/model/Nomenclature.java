package tao.core.model;


import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
class Nomenclature {
    private String resourceName;
    private String enabled;
    private String databaseTable;
    private String primaryKey;
    private Map<String, String> output;
    private Paging paging;
    private Sort sort;
    private String enabledFieldsSelection;
    private List<Clause> clause;
    private Cache cache;
    private Summary summary;
    private List<String> produces;
}
