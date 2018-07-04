package com.bnpparibas.dsibddf.nomenclature.domain.core.model;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@Setter
public class Nomenclature {

    public static final Nomenclature NONE = Nomenclature.builder().resourceName("unavailable resource").enabled(false).build();

    private String resourceName;
    @Getter(AccessLevel.NONE) private Boolean enabled;
    private String databaseTable;
    private String primaryKey;
    private Map<String, String> output;
    private Paging paging;
    private Sort sort;
    private String enabledFieldsSelection;
    private List<Clause> clauses;
    private Cache cache;
    private Summary summary;
    private List<String> produces;

    public Boolean isEnabled(){
        return this.enabled;
    }
}
