package com.bnpparibas.dsibddf.nomenclature.application.usecase.core.interactor;

import com.bnpparibas.dsibddf.nomenclature.domain.format.adapter.MediaType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bnpparibas.dsibddf.nomenclature.application.usecase.UseCase;
import com.bnpparibas.dsibddf.nomenclature.domain.core.NomenclatureConfig;
import com.bnpparibas.dsibddf.nomenclature.domain.core.NomenclatureRepository;
import com.bnpparibas.dsibddf.nomenclature.domain.core.model.Nomenclature;
import com.bnpparibas.dsibddf.nomenclature.domain.core.model.QueryParameters;
import com.bnpparibas.dsibddf.nomenclature.domain.core.model.Summary;
import com.bnpparibas.dsibddf.nomenclature.domain.format.adapter.ContentTypeProducer;
import com.bnpparibas.dsibddf.nomenclature.domain.format.adapter.ContentTypeProducerFactory;
import com.bnpparibas.dsibddf.nomenclature.application.usecase.core.service.QueryParametersFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Named
@Slf4j
public class GetSortPagingItemListUseCase extends UseCase<GetSortPagingItemListUseCase.RawResponse, GetSortPagingItemListUseCase.Params> {

    private final NomenclatureConfig nomenclatureConfig;
    private final QueryParametersFactory queryParametersFactory;
    private final NomenclatureRepository nomenclatureRepository;
    private final ContentTypeProducerFactory contentTypeProducerFactory;

    @Inject
    GetSortPagingItemListUseCase(QueryParametersFactory queryParametersFactory,
                                 NomenclatureRepository nomenclatureRepository,
                                 ContentTypeProducerFactory contentTypeProducerFactory,
                                 NomenclatureConfig nomenclatureConfig){
        this.nomenclatureConfig = nomenclatureConfig;
        this.queryParametersFactory = queryParametersFactory;
        this.nomenclatureRepository = nomenclatureRepository;
        this.contentTypeProducerFactory = contentTypeProducerFactory;
    }

    @Override
    public GetSortPagingItemListUseCase.RawResponse execute(GetSortPagingItemListUseCase.Params params) throws SQLException {
        val defaultConfig = nomenclatureConfig.getDefaultConfig(params.getNomenclatureName());
        if (!defaultConfig.equals(Nomenclature.NONE)) {
            val queryParameters = queryParametersFactory.create(params, defaultConfig);
            val items = nomenclatureRepository.getAllItemsBySortPaging(queryParameters, defaultConfig);
            val contentTypeProducer = contentTypeProducerFactory.create(params.getHeader());
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put(defaultConfig.getResourceName(), items);
            val summary = defaultConfig.getSummary();
            if (summary.isEnabled()) {
                resultMap.put(summary.getNbElementsAttributeName(), items.size());
                resultMap.put(summary.getTotalAttributeName(), nomenclatureRepository.countAllItems(defaultConfig));
            }
            final String bodyString = contentTypeProducer.produce(resultMap);
            LOGGER.debug(String.format("The response body string : %s", bodyString));
            return RawResponse.builder().statusCode(200).header(contentTypeProducer.getHttpContentTypeHeader()).bodyString(bodyString).build();
        }
        return RawResponse.builder().statusCode(404).bodyString(String.format("The %s asked is not found", params.getNomenclatureName())).build();
    }

    @Builder
    @Getter
    @ToString
    public static final class Params {
        private String nomenclatureName;
        private List<String> header;
        @Builder.Default
        private Optional<String> selectedFields = Optional.empty();
        @Builder.Default
        private Optional<String> sortField = Optional.empty();
        @Builder.Default
        private Optional<String> sortDirection = Optional.empty();
        @Builder.Default
        private Optional<String> pagingPacket = Optional.empty();
        @Builder.Default
        private Optional<String> offset = Optional.empty();
    }

    @Getter
    @Builder
    @ToString
    public static final class RawResponse {
        @Builder.Default
        private Integer statusCode = -1;
        @Builder.Default
        private String header = MediaType.APPLICATION_JSON_VALUE.getValue();
        @Builder.Default
        private String bodyString = "";
    }
}
