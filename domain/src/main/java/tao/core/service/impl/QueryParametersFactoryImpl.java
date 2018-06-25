package tao.core.service.impl;

import tao.core.QueryParametersFactory;
import tao.core.model.Nomenclature;
import tao.core.model.QueryParameters;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Named
class QueryParametersFactoryImpl implements QueryParametersFactory {

    @Inject
    QueryParametersFactoryImpl() {

    }

    @Override
    public QueryParameters create(QueryParameters.UserRequest userRequest, Nomenclature defaultConfig) {
        return QueryParameters.builder()
                // FIXME: 25/06/2018 current is for sortField=field1,field2,field3
                .selectedFields(fixSelectedFields(extractSelectedFields(userRequest.getSelectedFields()), defaultConfig.getOutput()))
                .sortField(fixSortField(userRequest.getSortField(), defaultConfig.getSort().getFields()))
                .build();

    }

    // TODO: 25/06/2018 add test
    private String fixSortField(Optional<String> sortField, List<String> defaultSortFields) {
        return sortField.filter(customeFilter(defaultSortFields)).orElse(defaultSortFields.get(0));
    }

    private Predicate<String> customeFilter(List<String> defaultSortFields) {
        return x -> !x.isEmpty() && defaultSortFields.contains(x);
    }


    // TODO: 25/06/2018 add test
    Map<String, String> fixSelectedFields(List<String> queriedSelectedFields, Map<String, String> defaultMap) {
        return queriedSelectedFields.isEmpty() ? defaultMap : defaultMap.entrySet().stream()
                .filter(x -> queriedSelectedFields.contains(x.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<String> extractSelectedFields(Optional<String> selectedFieldsOptional) {
        return selectedFieldsOptional.map(s -> Arrays.asList(s.split(","))).orElse(Collections.emptyList());
    }

}
