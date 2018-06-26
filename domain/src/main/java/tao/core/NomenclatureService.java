package tao.core;

import tao.core.model.Nomenclature;
import tao.core.model.QueryParameters;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface NomenclatureService {
    Optional<Nomenclature> getDefaultNomenclatureConfig(final String name);

    List<Map> getAllItems(final QueryParameters queryParameters, final Nomenclature defaultNomenclatureConfig);
}
