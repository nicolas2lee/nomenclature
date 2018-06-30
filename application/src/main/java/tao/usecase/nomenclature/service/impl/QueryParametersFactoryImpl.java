package tao.usecase.nomenclature.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tao.usecase.nomenclature.core.model.Nomenclature;
import tao.usecase.nomenclature.core.model.Paging;
import tao.usecase.nomenclature.core.model.QueryParameters;
import tao.usecase.nomenclature.core.model.Sort;
import tao.usecase.nomenclature.interactor.GetSortPagingNomenclatureListUseCase;
import tao.usecase.nomenclature.service.QueryParametersFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Named
class QueryParametersFactoryImpl implements QueryParametersFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryParametersFactoryImpl.class);

    @Inject
    QueryParametersFactoryImpl() {

    }

    @Override
    public QueryParameters create(GetSortPagingNomenclatureListUseCase.Params params, Nomenclature defaultNomenclatureConfig) {
        final Sort defaultSort = defaultNomenclatureConfig.getSort();
        final Paging defaultPaging = defaultNomenclatureConfig.getPaging();
        return QueryParameters.builder()
                //25/06/2018 current is for sortField=field1,field2,field3
                .selectedFields(fixSelectedFields(extractSelectedFields(params.getSelectedFields()), defaultNomenclatureConfig.getOutput()))
                .sortField(fixSort(params.getSortField(), defaultSort.getFields()))
                .sortDirection(fixSort(params.getSortDirection(), defaultSort.getDirection()))
                .pagingPacket(fixPagingNumber(params.getPagingPacket(), defaultPaging.getPacket()))
                .offset(fixPagingNumber(params.getOffset(), "0"))
                .build();

    }

    // FIXME: 26/06/2018 may be should limit the max value
    String fixPagingNumber(Optional<String> numberOptional, String defaultValue) {
        return numberOptional.filter(isPositive()).orElse(defaultValue);
    }

    private Predicate<String> isPositive() {
        return s -> {
            try {
                return Integer.valueOf(s) > 0;
            } catch (NumberFormatException e) {
                LOGGER.error(String.format("Could not convert string value %s to bigdecimal with exception %s", s, e.getMessage()));
                return false;
            }
        };
    }

    String fixSort(Optional<String> sortOptional, List<String> defaultSort) {
        return sortOptional.filter(isUserRequestSortFieldAvailable(defaultSort)).orElse(defaultSort.get(0));
    }

    private Predicate<String> isUserRequestSortFieldAvailable(List<String> defaultSortFields) {
        return x -> !x.isEmpty() && defaultSortFields.contains(x);
    }

    Map<String, String> fixSelectedFields(List<String> queriedSelectedFields, Map<String, String> defaultMap) {
        return queriedSelectedFields.isEmpty() ? defaultMap : defaultMap.entrySet().stream()
                .filter(x -> queriedSelectedFields.contains(x.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<String> extractSelectedFields(Optional<String> selectedFieldsOptional) {
        return selectedFieldsOptional.map(s -> Arrays.asList(s.split(","))).orElse(Collections.emptyList());
    }

}
