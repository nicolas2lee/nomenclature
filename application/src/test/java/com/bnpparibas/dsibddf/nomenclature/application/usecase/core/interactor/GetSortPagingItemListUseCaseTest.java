package com.bnpparibas.dsibddf.nomenclature.application.usecase.core.interactor;


import com.bnpparibas.dsibddf.nomenclature.application.usecase.core.service.QueryParametersFactory;
import com.bnpparibas.dsibddf.nomenclature.domain.core.NomenclatureConfig;
import com.bnpparibas.dsibddf.nomenclature.domain.core.NomenclatureRepository;
import com.bnpparibas.dsibddf.nomenclature.domain.core.model.Nomenclature;
import com.bnpparibas.dsibddf.nomenclature.domain.format.adapter.ContentTypeProducerFactory;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GetSortPagingItemListUseCaseTest {

    private GetSortPagingItemListUseCase getSortPagingItemListUseCase;

    @Mock
    private NomenclatureConfig nomenclatureConfig;
    private QueryParametersFactory queryParametersFactory;
    private NomenclatureRepository nomenclatureRepository;
    private ContentTypeProducerFactory contentTypeProducerFactory;

    @Before
    public void setUp() throws Exception {
        getSortPagingItemListUseCase = new GetSortPagingItemListUseCase(queryParametersFactory, nomenclatureRepository, contentTypeProducerFactory, nomenclatureConfig);
    }

    @Test
    public void should_retun_rawresponse_with_status_code_404_when_nomenclature_yaml_file_not_found() throws SQLException {
        given(nomenclatureConfig.getDefaultConfig(any())).willReturn(Nomenclature.NONE);
        val params = GetSortPagingItemListUseCase.Params.builder().build();

        val result = getSortPagingItemListUseCase.execute(params);

        assertThat(result.getStatusCode()).isEqualTo(404);
    }
}