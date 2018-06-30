package tao.usecase.nomenclature.interactor;

import lombok.Builder;
import lombok.Getter;
import tao.usecase.nomenclature.core.NomenclatureConfig;
import tao.usecase.nomenclature.core.NomenclatureRepository;
import tao.usecase.nomenclature.format.adapter.ResponseContentTypeFactory;
import tao.usecase.nomenclature.core.model.Nomenclature;
import tao.usecase.nomenclature.core.model.QueryParameters;
import tao.usecase.nomenclature.format.adapter.ContentTypeFactory;
import tao.usecase.UseCase;
import tao.usecase.nomenclature.service.QueryParametersFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Named
public class GetSingleNomenclatureUseCase extends UseCase<GetSingleNomenclatureUseCase.RawResponse, GetSingleNomenclatureUseCase.Params> {

    private final NomenclatureConfig nomenclatureConfig;
    private final QueryParametersFactory queryParametersFactory;
    private final NomenclatureRepository nomenclatureRepository;
    private final ResponseContentTypeFactory responseContentTypeFactory;



    @Inject
    GetSingleNomenclatureUseCase(NomenclatureConfig nomenclatureConfig,
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
            GetSortPagingNomenclatureListUseCase.Params adaptedParams = GetSortPagingNomenclatureListUseCase.Params.builder()
                    .nomenclatureName(params.getNomenclatureName())
                    .header(params.header)
                    .selectedFields(params.getSelectedFields())
                    .sortField(Optional.empty())
                    .sortDirection(Optional.empty())
                    .pagingPacket(Optional.empty())
                    .offset(Optional.empty())
                    .build();
            final QueryParameters queryParameters = queryParametersFactory.create(adaptedParams, defaultConfig);
            final ContentTypeFactory contentTypeFactory = responseContentTypeFactory.create(params.getHeader());
            Map<String, Object> item = nomenclatureRepository.getItemById(defaultConfig, params.getId(), queryParameters);

            return RawResponse.builder().statusCode(200).header(contentTypeFactory.getHttpContentTypeHeader()).bodyString(contentTypeFactory.produce(item)).build();
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
