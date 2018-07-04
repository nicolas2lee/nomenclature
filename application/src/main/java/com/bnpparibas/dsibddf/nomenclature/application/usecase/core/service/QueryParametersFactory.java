package com.bnpparibas.dsibddf.nomenclature.application.usecase.core.service;

import com.bnpparibas.dsibddf.nomenclature.domain.core.model.Nomenclature;
import com.bnpparibas.dsibddf.nomenclature.domain.core.model.QueryParameters;
import com.bnpparibas.dsibddf.nomenclature.application.usecase.core.interactor.GetSortPagingItemListUseCase;

public interface QueryParametersFactory {
    QueryParameters create(GetSortPagingItemListUseCase.Params params,
                           Nomenclature defaultNomenclatureConfig);
}
