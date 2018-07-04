package tao.usecase.nomenclature.service;

import tao.usecase.nomenclature.core.model.Nomenclature;
import tao.usecase.nomenclature.core.model.QueryParameters;
import tao.usecase.nomenclature.interactor.GetSortPagingItemListUseCase;

public interface QueryParametersFactory {
    QueryParameters create(GetSortPagingItemListUseCase.Params params,
                           Nomenclature defaultNomenclatureConfig);
}
