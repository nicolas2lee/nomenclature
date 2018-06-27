package tao.features.core;

import tao.features.core.model.Nomenclature;
import tao.features.core.model.QueryParameters;

public interface QueryParametersFactory {
    QueryParameters create(QueryParameters.UserRequest userRequest,
                           Nomenclature defaultNomenclatureConfig);
}
