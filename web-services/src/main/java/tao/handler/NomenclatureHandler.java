package tao.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tao.core.NomenclatureService;
import tao.core.model.Nomenclature;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class NomenclatureHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(NomenclatureHandler.class);

    private static final String SORT_FIELD_QUERY_PARAMETER = "sortField";
    private static final String SORT_DIRECTION_QUERY_PARAMETER = "sortDirection";
    private static final String PAGING_PACKET_QUERY_PARAMETER = "pagingPacket";
    private static final String OFFSET_QUERY_PARAMETER = "offset";

    private final NomenclatureService nomenclatureService;

    NomenclatureHandler(NomenclatureService nomenclatureService){
        this.nomenclatureService = nomenclatureService;
    }

    public Mono<ServerResponse> list(final ServerRequest request){
        LOGGER.info(String.format("%s %s", request.methodName(), request.path()));
        Nomenclature nomenclature = nomenclatureService.getNomenclature(request.pathVariable("nomenclatureName"))
                .orElse(Nomenclature.NONE);

        Nomenclature.QueryParameters queryParameters = buildNomenclatureQueryParameters(request, nomenclature);

        return ServerResponse.ok().body(Flux.range(1, 1000000000).map(x-> String.valueOf(x)), String.class);
    }

    private Nomenclature.QueryParameters buildNomenclatureQueryParameters(final ServerRequest request, Nomenclature nomenclature) {
        // FIXME: 25/06/2018 current is for sortField=field1,field2,field3
        List<String> queriedSelectedFields = getQueriedSelectedFields(request.queryParam(SORT_FIELD_QUERY_PARAMETER));
        Map<String, String> finalSelectedFields = getFinalSelectedFields(nomenclature, queriedSelectedFields);

        Optional<String> sortDirection = request.queryParam(SORT_DIRECTION_QUERY_PARAMETER);
        Optional<String> pagingPacket = request.queryParam(PAGING_PACKET_QUERY_PARAMETER);
        Optional<String> offset = request.queryParam(OFFSET_QUERY_PARAMETER);
        return Nomenclature.QueryParameters.builder()
                .selectedFields(finalSelectedFields)
                .build();
    }

    // TODO: 25/06/2018 add test
    Map<String, String> getFinalSelectedFields(Nomenclature nomenclature, List<String> queriedSelectedFields) {
        return queriedSelectedFields.isEmpty() ? nomenclature.getOutput() : nomenclature.getOutput().entrySet().stream()
                .filter(x -> queriedSelectedFields.contains(x.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<String> getQueriedSelectedFields(Optional<String> selectedFieldsOptional) {
        return selectedFieldsOptional.map(s -> Arrays.asList(s.split(","))).orElse(Collections.emptyList());
    }
}
