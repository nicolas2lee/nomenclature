package tao.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tao.core.QueryParametersFactory;
import tao.core.model.Nomenclature;
import tao.core.model.Paging;
import tao.core.model.QueryParameters;
import tao.core.model.Sort;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
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
    public QueryParameters create(QueryParameters.UserRequest userRequest, Nomenclature defaultConfig) {
        final Sort defaultSort = defaultConfig.getSort();
        final Paging defaultPaging = defaultConfig.getPaging();
        return QueryParameters.builder()
                //25/06/2018 current is for sortField=field1,field2,field3
                .selectedFields(fixSelectedFields(extractSelectedFields(userRequest.getSelectedFields()), defaultConfig.getOutput()))
                .sortField(fixSort(userRequest.getSortField(), defaultSort.getFields()))
                .sortDirection(fixSort(userRequest.getSortDirection(), defaultSort.getDirection()))
                .pagingPacket(fixPagingNumber(userRequest.getPagingPacket(), defaultPaging.getPacket()))
                .offset(fixPagingNumber(userRequest.getOffset(), "0"))
                .build();

    }

    // FIXME: 26/06/2018 may be should limit the max value
    String fixPagingNumber(Optional<String> numberOptional, String defaultValue) {
        return numberOptional.filter(isPositive()).orElse(defaultValue);
    }

    private Predicate<String> isPositive() {
        return s -> {
            BigDecimal number;
            try {
                number = new BigDecimal(s);
                return number.signum() > 0;
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
