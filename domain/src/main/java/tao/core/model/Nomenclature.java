package tao.core.model;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Builder
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
    private List<Clause> clause;
    private Cache cache;
    private Summary summary;
    private List<String> produces;

    public Boolean isEnabled(){
        return this.enabled;
    }

    @Builder
    public static class QueryParameters{
        private Map<String, String> selectedFields;
        private String sortField;
        private String sortDirection;
        private String pagingPacket;
        private String offset;
    }
}
