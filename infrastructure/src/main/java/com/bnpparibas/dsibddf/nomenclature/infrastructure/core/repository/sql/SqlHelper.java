package com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.sql;

import com.bnpparibas.dsibddf.nomenclature.domain.core.model.Nomenclature;
import com.bnpparibas.dsibddf.nomenclature.domain.core.model.QueryParameters;
import com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.sql.impl.SqlHelperResult;

public interface SqlHelper {
    SqlHelperResult buildAllItemsBySortPaging(final QueryParameters queryParameters, final Nomenclature defaultConfig);

    SqlHelperResult buildSingleItem(final QueryParameters queryParameters, final Nomenclature defaultConfig);
}
