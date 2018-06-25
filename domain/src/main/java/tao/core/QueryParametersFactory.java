package tao.core;

import tao.core.model.Nomenclature;
import tao.core.model.QueryParameters;

public interface QueryParametersFactory {
    QueryParameters create(QueryParameters.UserRequest userRequest,
                           Nomenclature defaultConfig);
}
