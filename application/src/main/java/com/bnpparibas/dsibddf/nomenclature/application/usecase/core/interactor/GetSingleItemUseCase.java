package com.bnpparibas.dsibddf.nomenclature.application.usecase.core.interactor;

import com.bnpparibas.dsibddf.nomenclature.domain.format.adapter.MediaType;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bnpparibas.dsibddf.nomenclature.application.usecase.UseCase;
import com.bnpparibas.dsibddf.nomenclature.domain.core.NomenclatureConfig;
import com.bnpparibas.dsibddf.nomenclature.domain.core.NomenclatureRepository;
import com.bnpparibas.dsibddf.nomenclature.domain.core.model.Nomenclature;
import com.bnpparibas.dsibddf.nomenclature.domain.core.model.QueryParameters;
import com.bnpparibas.dsibddf.nomenclature.domain.format.adapter.ContentTypeProducer;
import com.bnpparibas.dsibddf.nomenclature.domain.format.adapter.ContentTypeProducerFactory;
import com.bnpparibas.dsibddf.nomenclature.application.usecase.core.service.QueryParametersFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Named
@Slf4j
public class GetSingleItemUseCase extends UseCase<GetSingleItemUseCase.RawResponse, GetSingleItemUseCase.Params> {

    private final NomenclatureConfig nomenclatureConfig;
    private final QueryParametersFactory queryParametersFactory;
    private final NomenclatureRepository nomenclatureRepository;
    private final ContentTypeProducerFactory contentTypeProducerFactory;

    @Inject
    GetSingleItemUseCase(NomenclatureConfig nomenclatureConfig,
                         QueryParametersFactory queryParametersFactory,
                         ContentTypeProducerFactory contentTypeProducerFactory,
                         NomenclatureRepository nomenclatureRepository){
        this.nomenclatureConfig = nomenclatureConfig;
        this.queryParametersFactory = queryParametersFactory;
        this.contentTypeProducerFactory = contentTypeProducerFactory;
        this.nomenclatureRepository = nomenclatureRepository;
    }

    public RawResponse execute(Params params) throws SQLException {
        val defaultConfig = nomenclatureConfig.getDefaultConfig(params.getNomenclatureName());
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
            val queryParameters = queryParametersFactory.create(adaptedParams, defaultConfig);
            val contentTypeProducer = contentTypeProducerFactory.create(params.getHeader());
            Map<String, Object> item = nomenclatureRepository.getItemById(defaultConfig, params.getId(), queryParameters);
            return RawResponse.builder().statusCode(200).header(contentTypeProducer.getHttpContentTypeHeader()).bodyString(contentTypeProducer.produce(item)).build();
        }
        return RawResponse.builder().statusCode(404).bodyString(String.format("The %s asked is not found", params.getNomenclatureName())).build();
    }

    @Builder
    @Getter
    public static final class Params {
        @Builder.Default
        private String id = "-1";
        private String nomenclatureName;
        private List<String> header;
        @Builder.Default
        private Optional<String> selectedFields = Optional.empty();
    }

    @Getter
    @Builder
    public static final class RawResponse {
        @Builder.Default
        private Integer statusCode = -1;
        @Builder.Default
        private String header = MediaType.APPLICATION_JSON_VALUE.getValue();
        private String bodyString;
    }
}
