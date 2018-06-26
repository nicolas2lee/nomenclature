package tao.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tao.core.NomenclatureService;
import tao.core.QueryParametersFactory;
import tao.core.model.Nomenclature;
import tao.core.model.QueryParameters;

import java.util.List;
import java.util.Map;

@Component
public class NomenclatureHandler {
    //https://blog.csdn.net/sxdtzhaoxinguo/article/details/79235998
    private static final Logger LOGGER = LoggerFactory.getLogger(NomenclatureHandler.class);

    private static final String SELECTED_FIELDS_QUERY_PARAMETER = "selectedFields";
    private static final String SORT_FIELD_QUERY_PARAMETER = "sortField";
    private static final String SORT_DIRECTION_QUERY_PARAMETER = "sortDirection";
    private static final String PAGING_PACKET_QUERY_PARAMETER = "pagingPacket";
    private static final String OFFSET_QUERY_PARAMETER = "offset";

    private final NomenclatureService nomenclatureService;

    private final QueryParametersFactory queryParametersFactory;

    NomenclatureHandler(NomenclatureService nomenclatureService,
                        QueryParametersFactory queryParametersFactory) {
        this.nomenclatureService = nomenclatureService;
        this.queryParametersFactory = queryParametersFactory;
    }

    public Mono<ServerResponse> list(final ServerRequest request){
        LOGGER.info(String.format("%s %s", request.methodName(), request.path()));
        final Nomenclature defaultNomenclatureConfig = nomenclatureService.getDefaultNomenclatureConfig(request.pathVariable("nomenclatureName"))
                .orElse(Nomenclature.NONE);
        final QueryParameters queryParameters = buildNomenclatureQueryParameters(request, defaultNomenclatureConfig);

        List<Map> items = nomenclatureService.getAllItems(queryParameters, defaultNomenclatureConfig);

        return ServerResponse.ok().body(Flux.range(1, 1000000000).map(x-> String.valueOf(x)), String.class);
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
