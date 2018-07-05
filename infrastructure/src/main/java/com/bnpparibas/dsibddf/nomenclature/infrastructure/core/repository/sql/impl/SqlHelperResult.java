package com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.sql.impl;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SqlHelperResult {
    private String selectedFields;
    private String whereClauses;
    private String orderByFields;
    private String orderByDirection;
    private String limitClause;
}
