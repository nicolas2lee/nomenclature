package tao.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import tao.features.core.NomenclatureService;
import tao.features.core.QueryParametersFactory;
import tao.features.core.ResponseContentTypeFactory;
import tao.features.core.model.Nomenclature;
import tao.features.core.model.QueryParameters;
import tao.features.core.model.Summary;
import tao.features.format.adapter.ContentTypeFactory;

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
    private static final String ID_QUERY_PARAMETER = "ID";

    private final NomenclatureService nomenclatureService;

    private final QueryParametersFactory queryParametersFactory;

    private final ResponseContentTypeFactory responseContentTypeFactory;

    NomenclatureHandler(NomenclatureService nomenclatureService,
                        QueryParametersFactory queryParametersFactory,
                        ResponseContentTypeFactory responseContentTypeFactory) {
        this.nomenclatureService = nomenclatureService;
        this.queryParametersFactory = queryParametersFactory;
        this.responseContentTypeFactory = responseContentTypeFactory;
    }

    // TODO: 28/06/2018 should add response http header
    public Mono<ServerResponse> list(final ServerRequest request){
        final Nomenclature defaultConfig = nomenclatureService.getDefaultNomenclatureConfig(request.pathVariable("nomenclatureName"))
                .orElse(Nomenclature.NONE);
        LOGGER.info(String.format("%s %s", request.methodName(), request.path()));
        if (!defaultConfig.equals(Nomenclature.NONE)) {
            final QueryParameters queryParameters = buildNomenclatureQueryParameters(request, defaultConfig);
            List<Map<String, Object>> items = nomenclatureService.getAllItemsBySortPaging(queryParameters, defaultConfig);
            List<String> header = request.headers().header("accept");
            final ContentTypeFactory contentTypeFactory = responseContentTypeFactory.create(header);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put(defaultConfig.getResourceName(), items);
            final Summary summary = defaultConfig.getSummary();
            if (summary.isEnabled()) {
                resultMap.put(summary.getNbElementsAttributeName(), items.size());
                resultMap.put(summary.getTotalAttributeName(), nomenclatureService.countAllItems(defaultConfig));
            }
            final String bodyString = contentTypeFactory.produce(resultMap);
            LOGGER.debug(String.format("The response body string : %s", bodyString));

            return ServerResponse.ok().body(Mono.just(bodyString), String.class);
        }
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Mono.just("Failed to get nomenclature default config"), String.class);
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

    public Mono<ServerResponse> show(final ServerRequest request) {
        final Nomenclature defaultConfig = nomenclatureService.getDefaultNomenclatureConfig(request.pathVariable("nomenclatureName"))
                .orElse(Nomenclature.NONE);
        LOGGER.info(String.format("%s %s", request.methodName(), request.path()));
        if (!defaultConfig.equals(Nomenclature.NONE)) {
            final QueryParameters queryParameters = buildNomenclatureQueryParameters(request, defaultConfig);
            final String id = request.pathVariable(ID_QUERY_PARAMETER);
            List<String> header = request.headers().header("accept");
            final ContentTypeFactory contentTypeFactory = responseContentTypeFactory.create(header);
            Map<String, Object> item = nomenclatureService.getItemById(defaultConfig, id, queryParameters);
            return ServerResponse.ok().body(Mono.just(contentTypeFactory.produce(item)), String.class);
        }
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Mono.just("Failed to get nomenclature default config"), String.class);

    }
}
