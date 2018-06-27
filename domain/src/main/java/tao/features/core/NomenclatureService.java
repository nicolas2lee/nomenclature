package tao.features.core;

import tao.features.core.model.Nomenclature;
import tao.features.core.model.QueryParameters;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface NomenclatureService {
    Optional<Nomenclature> getDefaultNomenclatureConfig(final String name);

    List<Map<String, Object>> getAllItems(final QueryParameters queryParameters, final Nomenclature defaultNomenclatureConfig);
}
