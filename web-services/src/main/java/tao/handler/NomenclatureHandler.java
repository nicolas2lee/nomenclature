package tao.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import tao.features.core.NomenclatureService;
import tao.features.core.QueryParametersFactory;
import tao.features.core.ResponseContentProducer;
import tao.features.core.model.Nomenclature;
import tao.features.core.model.QueryParameters;
import tao.features.core.model.Summary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NomenclatureHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(NomenclatureHandler.class);

    private static final String SELECTED_FIELDS_QUERY_PARAMETER = "selectedFields";
    private static final String SORT_FIELD_QUERY_PARAMETER = "sortField";
    private static final String SORT_DIRECTION_QUERY_PARAMETER = "sortDirection";
    private static final String PAGING_PACKET_QUERY_PARAMETER = "pagingPacket";
    private static final String OFFSET_QUERY_PARAMETER = "offset";

    private final NomenclatureService nomenclatureService;

    private final QueryParametersFactory queryParametersFactory;

    private final ResponseContentProducer responseContentProducer;

    NomenclatureHandler(NomenclatureService nomenclatureService,
                        QueryParametersFactory queryParametersFactory,
                        ResponseContentProducer responseContentProducer) {
        this.nomenclatureService = nomenclatureService;
        this.queryParametersFactory = queryParametersFactory;
        this.responseContentProducer = responseContentProducer;
    }

    public Mono<ServerResponse> list(final ServerRequest request){
        LOGGER.info(String.format("%s %s", request.methodName(), request.path()));
        final Nomenclature defaultNomenclatureConfig = nomenclatureService.getDefaultNomenclatureConfig(request.pathVariable("nomenclatureName"))
                .orElse(Nomenclature.NONE);
        final QueryParameters queryParameters = buildNomenclatureQueryParameters(request, defaultNomenclatureConfig);
        List<Map<String, Object>> items = nomenclatureService.getAllItemsBySortPaging(queryParameters, defaultNomenclatureConfig);
        List<String> header = request.headers().header("accept");
        responseContentProducer.create(header);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(defaultNomenclatureConfig.getResourceName(), items);
        final Summary summary = defaultNomenclatureConfig.getSummary();
        if (summary.isEnabled()) {
            resultMap.put(summary.getNbElementsAttributeName(), items.size());
            resultMap.put(summary.getTotalAttributeName(), nomenclatureService.countAllItems(defaultNomenclatureConfig));
        }
        final String bodyString = responseContentProducer.produce(resultMap);
        LOGGER.debug(String.format("The response body string : %s", bodyString));

        return ServerResponse.ok().body(Mono.just(bodyString), String.class);
    }

    private QueryParameters buildNomenclatureQueryParameters(final ServerRequest request, final Nomenclature defaultNomenclatureConfig) {
        final QueryParameters.UserRequest userRequest = buildUserRequestQueryParameters(request);
        return queryParametersFactory.create(userRequest, defaultNomenclatureConfig);
    }

    private QueryParameters.UserRequest buildUserRequestQueryParameters(final ServerRequest request) {
        return QueryParameters.UserRequest.builder()
                .selectedFields(request.queryParam(SELECTED_FIELDS_QUERY_PARAMETER))
                .sortField(request.queryParam(SORT_FIELD_QUERY_PARAMETER)).sortDirection(request.queryParam(SORT_DIRECTION_QUERY_PARAMETER))
                .pagingPacket(request.queryParam(PAGING_PACKET_QUERY_PARAMETER)).offset(request.queryParam(OFFSET_QUERY_PARAMETER))
                .build();
    }
}
