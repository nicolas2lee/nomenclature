package tao.usecase.nomenclature.interactor;

import lombok.Builder;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tao.usecase.nomenclature.core.NomenclatureConfig;
import tao.usecase.nomenclature.core.NomenclatureRepository;
import tao.usecase.nomenclature.format.adapter.ResponseContentTypeFactory;
import tao.usecase.nomenclature.service.QueryParametersFactory;
import tao.usecase.nomenclature.core.model.Nomenclature;
import tao.usecase.nomenclature.core.model.QueryParameters;
import tao.usecase.nomenclature.core.model.Summary;
import tao.usecase.nomenclature.format.adapter.ContentTypeFactory;
import tao.usecase.UseCase;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Named
public class GetSortPagingNomenclatureListUseCase extends UseCase<GetSortPagingNomenclatureListUseCase.RawResponse, GetSortPagingNomenclatureListUseCase.Params> {
    private final static Logger LOGGER = LoggerFactory.getLogger(GetSortPagingNomenclatureListUseCase.class);

    private final NomenclatureConfig nomenclatureConfig;
    private final QueryParametersFactory queryParametersFactory;
    private final NomenclatureRepository nomenclatureRepository;
    private final ResponseContentTypeFactory responseContentTypeFactory;

    @Inject
    GetSortPagingNomenclatureListUseCase(QueryParametersFactory queryParametersFactory,
                                         NomenclatureRepository nomenclatureRepository,
                                         ResponseContentTypeFactory responseContentTypeFactory,
                                         NomenclatureConfig nomenclatureConfig){
        this.nomenclatureConfig = nomenclatureConfig;
        this.queryParametersFactory = queryParametersFactory;
        this.nomenclatureRepository = nomenclatureRepository;
        this.responseContentTypeFactory =responseContentTypeFactory;
    }

    @Override
    public GetSortPagingNomenclatureListUseCase.RawResponse execute(GetSortPagingNomenclatureListUseCase.Params params){
        final Nomenclature defaultConfig = nomenclatureConfig.getDefaultConfig(params.getNomenclatureName());
        if (!defaultConfig.equals(Nomenclature.NONE)) {
            final QueryParameters queryParameters = queryParametersFactory.create(params, defaultConfig);
            List<Map<String, Object>> items = nomenclatureRepository.getAllItemsBySortPaging(queryParameters, defaultConfig);
            final ContentTypeFactory contentTypeFactory = responseContentTypeFactory.create(params.getHeader());
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put(defaultConfig.getResourceName(), items);
            final Summary summary = defaultConfig.getSummary();
            if (summary.isEnabled()) {
                resultMap.put(summary.getNbElementsAttributeName(), items.size());
                resultMap.put(summary.getTotalAttributeName(), nomenclatureRepository.countAllItems(defaultConfig));
            }
            final String bodyString = contentTypeFactory.produce(resultMap);
            LOGGER.debug(String.format("The response body string : %s", bodyString));
            return RawResponse.builder().statusCode(200).header(contentTypeFactory.getHttpContentTypeHeader()).bodyString(bodyString).build();
        }
        return RawResponse.builder().statusCode(404).bodyString(String.format("The %s asked is not found", params.getNomenclatureName())).build();
    }

    @Builder
    @Getter
    public static final class Params {
        private String nomenclatureName;
        private List<String> header;
        private Optional<String> selectedFields;
        private Optional<String> sortField;
        private Optional<String> sortDirection;
        private Optional<String> pagingPacket;
        private Optional<String> offset;
    }

    @Getter
    @Builder
    public static final class RawResponse {
        private Integer statusCode;
        private String header;
        private String bodyString;
    }
}