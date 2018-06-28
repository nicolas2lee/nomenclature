package tao.features.core;

import tao.features.core.model.Nomenclature;
import tao.features.core.model.QueryParameters;

import java.util.List;
import java.util.Map;

public interface NomenclatureService {

    List<Map<String, Object>> getAllItemsBySortPaging(final QueryParameters queryParameters, final Nomenclature defaultNomenclatureConfig);

    Integer countAllItems(Nomenclature defaultConfig);

    Map<String, Object> getItemById(Nomenclature defaultConfig, String id, QueryParameters queryParameters);
}
