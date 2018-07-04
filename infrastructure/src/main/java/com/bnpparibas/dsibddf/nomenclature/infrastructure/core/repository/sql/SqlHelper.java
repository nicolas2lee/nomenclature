package com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.sql;

import com.bnpparibas.dsibddf.nomenclature.domain.core.model.Clause;
import com.bnpparibas.dsibddf.nomenclature.domain.core.model.Paging;

import java.util.List;
import java.util.Map;

public interface SqlHelper {
    String buildSelectedFields(Map<String, String> selectedFields);

    String buildLimitClause(Paging paging, String offset, String pagingPacket);

    String buildWhereClause(List<Clause> clauses);
}
