package tao.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import tao.usecase.nomenclature.interactor.GetSingleNomenclatureUseCase;
import tao.usecase.nomenclature.interactor.GetSortPagingNomenclatureListUseCase;

import java.util.List;

@Component
public class NomenclatureHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(NomenclatureHandler.class);

    private static final String SELECTED_FIELDS_QUERY_PARAMETER = "selectedFields";
    private static final String SORT_FIELD_QUERY_PARAMETER = "sortField";
    private static final String SORT_DIRECTION_QUERY_PARAMETER = "sortDirection";
    private static final String PAGING_PACKET_QUERY_PARAMETER = "pagingPacket";
    private static final String OFFSET_QUERY_PARAMETER = "offset";
    private static final String ID_QUERY_PARAMETER = "id";


    private final GetSortPagingNomenclatureListUseCase getSortPagingNomenclatureListUseCaseUseCase;
    private final GetSingleNomenclatureUseCase getSingleNomenclatureUseCase;

    NomenclatureHandler(GetSortPagingNomenclatureListUseCase getSortPagingNomenclatureListUseCaseUseCase,
                        GetSingleNomenclatureUseCase getSingleNomenclatureUseCase) {
        this.getSortPagingNomenclatureListUseCaseUseCase = getSortPagingNomenclatureListUseCaseUseCase;
        this.getSingleNomenclatureUseCase = getSingleNomenclatureUseCase;
    }

    // TODO: 28/06/2018 should use cache & in which scenario
    public Mono<ServerResponse> list(final ServerRequest request){
        LOGGER.info(String.format("%s %s", request.methodName(), request.path()));
        final GetSortPagingNomenclatureListUseCase.Params userRequest = buildGetSortPagingNomenclatureListParams(request);
        GetSortPagingNomenclatureListUseCase.RawResponse rawResponse = getSortPagingNomenclatureListUseCaseUseCase.execute(userRequest);
        return ServerResponse.status(rawResponse.getStatusCode()).header(rawResponse.getHeader()).body(Mono.just(rawResponse.getBodyString()), String.class);
    }

    private GetSortPagingNomenclatureListUseCase.Params buildGetSortPagingNomenclatureListParams(final ServerRequest request) {
        final String nomenclatureName = request.pathVariable("nomenclatureName");
        final List<String> header = request.headers().header("accept");
        return GetSortPagingNomenclatureListUseCase.Params.builder()
                .selectedFields(request.queryParam(SELECTED_FIELDS_QUERY_PARAMETER))
                .sortField(request.queryParam(SORT_FIELD_QUERY_PARAMETER)).sortDirection(request.queryParam(SORT_DIRECTION_QUERY_PARAMETER))
                .pagingPacket(request.queryParam(PAGING_PACKET_QUERY_PARAMETER)).offset(request.queryParam(OFFSET_QUERY_PARAMETER))
                .nomenclatureName(nomenclatureName)
                .header(header)
                .build();
    }

    public Mono<ServerResponse> show(final ServerRequest request) {
        LOGGER.info(String.format("%s %s", request.methodName(), request.path()));
        final GetSingleNomenclatureUseCase.Params params = buildGetSingleNomenclatureParams(request);
        GetSingleNomenclatureUseCase.RawResponse rawResponse = getSingleNomenclatureUseCase.execute(params);
        return ServerResponse.status(rawResponse.getStatusCode()).header(rawResponse.getHeader()).body(Mono.just(rawResponse.getBodyString()), String.class);
    }

    private GetSingleNomenclatureUseCase.Params buildGetSingleNomenclatureParams(ServerRequest request) {
        final String nomenclatureName = request.pathVariable("nomenclatureName");
        final String id = request.pathVariable(ID_QUERY_PARAMETER);
        final List<String> header = request.headers().header("accept");

        return GetSingleNomenclatureUseCase.Params.builder()
                .nomenclatureName(nomenclatureName)
                .id(id)
                .selectedFields(request.queryParam(SELECTED_FIELDS_QUERY_PARAMETER))
                .header(header)
                .build();
    }
}
