package tao.usecase.nomenclature.interactor;

import lombok.Builder;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tao.usecase.UseCase;
import tao.usecase.nomenclature.core.NomenclatureConfig;
import tao.usecase.nomenclature.core.NomenclatureRepository;
import tao.usecase.nomenclature.core.model.Nomenclature;
import tao.usecase.nomenclature.core.model.QueryParameters;
import tao.usecase.nomenclature.format.adapter.ContentTypeProducer;
import tao.usecase.nomenclature.format.adapter.ResponseContentTypeFactory;
import tao.usecase.nomenclature.service.QueryParametersFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Named
public class GetSingleItemUseCase extends UseCase<GetSingleItemUseCase.RawResponse, GetSingleItemUseCase.Params> {

    private final static Logger LOGGER = LoggerFactory.getLogger(GetSingleItemUseCase.class);

    private final NomenclatureConfig nomenclatureConfig;
    private final QueryParametersFactory queryParametersFactory;
    private final NomenclatureRepository nomenclatureRepository;
    private final ResponseContentTypeFactory responseContentTypeFactory;

    @Inject
    GetSingleItemUseCase(NomenclatureConfig nomenclatureConfig,
                         QueryParametersFactory queryParametersFactory,
                         ResponseContentTypeFactory responseContentTypeFactory,
                         NomenclatureRepository nomenclatureRepository){
        this.nomenclatureConfig = nomenclatureConfig;
        this.queryParametersFactory = queryParametersFactory;
        this.responseContentTypeFactory = responseContentTypeFactory;
        this.nomenclatureRepository = nomenclatureRepository;
    }

    public RawResponse execute(Params params){
        final Nomenclature defaultConfig = nomenclatureConfig.getDefaultConfig(params.getNomenclatureName());
        if (!defaultConfig.equals(Nomenclature.NONE)) {
            GetSortPagingItemListUseCase.Params adaptedParams = GetSortPagingItemListUseCase.Params.builder()
                    .nomenclatureName(params.getNomenclatureName())
                    .header(params.header)
                    .selectedFields(params.getSelectedFields())
                    .sortField(Optional.empty())
                    .sortDirection(Optional.empty())
                    .pagingPacket(Optional.empty())
                    .offset(Optional.empty())
                    .build();
            final QueryParameters queryParameters = queryParametersFactory.create(adaptedParams, defaultConfig);
            final ContentTypeProducer contentTypeProducer = responseContentTypeFactory.create(params.getHeader());
            try {
                Map<String, Object> item = nomenclatureRepository.getItemById(defaultConfig, params.getId(), queryParameters);
                return RawResponse.builder().statusCode(200).header(contentTypeProducer.getHttpContentTypeHeader()).bodyString(contentTypeProducer.produce(item)).build();
            } catch (SQLException e) {
                final String errorMessage = String.format("Sql exception with follow message: %s", e);
                LOGGER.error(errorMessage);
                return RawResponse.builder().statusCode(500).bodyString(errorMessage).build();
            }
        }
        return RawResponse.builder().statusCode(404).bodyString(String.format("The %s asked is not found", params.getNomenclatureName())).build();
    }

    @Builder
    @Getter
    public static final class Params {
        private String id;
        private String nomenclatureName;
        private List<String> header;
        private Optional<String> selectedFields;
    }

    @Getter
    @Builder
    public static final class RawResponse {
        private Integer statusCode;
        private String header;
        private String bodyString;
    }
}
