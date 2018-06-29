package tao.usecase.nomenclature.service;

import tao.usecase.nomenclature.core.model.Nomenclature;
import tao.usecase.nomenclature.core.model.QueryParameters;
import tao.usecase.nomenclature.interactor.GetSortPagingNomenclatureListUseCase;

public interface QueryParametersFactory {
    QueryParameters create(GetSortPagingNomenclatureListUseCase.Params params,
                           Nomenclature defaultNomenclatureConfig);
}
