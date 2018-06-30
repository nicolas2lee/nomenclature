package tao.usecase.nomenclature.core;

import tao.usecase.nomenclature.core.model.Nomenclature;
import tao.usecase.nomenclature.core.model.QueryParameters;

import java.util.List;
import java.util.Map;

public interface NomenclatureRepository {

    List<Map<String, Object>> getAllItemsBySortPaging(final QueryParameters queryParameters, final Nomenclature defaultNomenclatureConfig);

    Integer countAllItems(Nomenclature defaultConfig);

    Map<String, Object> getItemById(Nomenclature defaultConfig, String id, QueryParameters queryParameters);
}
