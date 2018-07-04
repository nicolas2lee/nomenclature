package com.bnpparibas.dsibddf.nomenclature.domain.core;

import com.bnpparibas.dsibddf.nomenclature.domain.core.model.Nomenclature;
import com.bnpparibas.dsibddf.nomenclature.domain.core.model.QueryParameters;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface NomenclatureRepository {

    List<Map<String, Object>> getAllItemsBySortPaging(final QueryParameters queryParameters, final Nomenclature defaultNomenclatureConfig) throws SQLException;

    Integer countAllItems(Nomenclature defaultConfig) throws SQLException;

    Map<String, Object> getItemById(Nomenclature defaultConfig, String id, QueryParameters queryParameters) throws SQLException;
}
