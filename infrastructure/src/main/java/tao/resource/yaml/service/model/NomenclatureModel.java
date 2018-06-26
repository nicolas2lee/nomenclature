package tao.resource.yaml.service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import tao.core.model.Nomenclature;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class NomenclatureModel {
    private String resourceName;
    private String enbled;
    private String dbTable;
    private String pk;
    private Map<String, String> output;
    private PagingModel paging;
    private SortModel sort;
    private String enabledFieldsSelection;
    private List<ClauseModel> clause;
    private CacheModel cache;
    private SummaryModel summary;
    private List<String> produces;

    public Nomenclature toDomainObject(){
        return Nomenclature.builder()
                .resourceName(resourceName)
                .enabled(enbled.equals("1"))
                .databaseTable(dbTable)
                .primaryKey(pk)
                .output(output)
                .paging(paging.toDomainObject())
                //.sort()
                .enabledFieldsSelection(enabledFieldsSelection)
                //.clause()
                //.cache()
                //.summary()
                .produces(produces)
                .build();
    }
}
