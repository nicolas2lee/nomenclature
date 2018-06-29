package tao.usecase.nomenclature.resource.yaml.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tao.usecase.nomenclature.core.model.Nomenclature;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
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

    public Nomenclature toDomain() {
        return Nomenclature.builder()
                .resourceName(resourceName)
                .enabled(enbled.equals("1"))
                .databaseTable(dbTable)
                .primaryKey(pk)
                .output(output)
                .paging(paging.toDomainObject())
                .sort(sort.toDomain())
                .enabledFieldsSelection(enabledFieldsSelection)
                .clauses(clause.stream().map(ClauseModel::toDomain).collect(Collectors.toList()))
                .cache(cache.toDomain())
                .summary(summary.toDomain())
                .produces(produces)
                .build();
    }
}
