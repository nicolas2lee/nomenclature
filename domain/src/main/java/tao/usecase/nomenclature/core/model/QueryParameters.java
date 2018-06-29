package tao.usecase.nomenclature.core.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class QueryParameters {
    private Map<String, String> selectedFields;
    private String sortField;
    private String sortDirection;
    private String pagingPacket;
    private String offset;
}
