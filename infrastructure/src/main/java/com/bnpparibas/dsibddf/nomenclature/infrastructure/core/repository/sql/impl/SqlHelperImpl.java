package com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.sql.impl;

import com.bnpparibas.dsibddf.nomenclature.domain.core.model.Clause;
import com.bnpparibas.dsibddf.nomenclature.domain.core.model.Nomenclature;
import com.bnpparibas.dsibddf.nomenclature.domain.core.model.Paging;
import com.bnpparibas.dsibddf.nomenclature.domain.core.model.QueryParameters;
import com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.sql.SqlHelper;
import com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.sql.exception.SqlWhereClauseFormatInvalidException;
import lombok.val;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Scope("prototype")
public class SqlHelperImpl implements SqlHelper {
    private static final String DEFAULT_WHERE_CLAUSE = "1=1";

    private Set<String> extractSelectedFieldsValuesFromMap(Map<String, String> selectedFields) {
        return selectedFields.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toSet());
    }

    @Override
    public SqlHelperResult buildAllItemsBySortPaging(QueryParameters queryParameters, Nomenclature defaultConfig) {
        val selectedFields = buildSelectedFields(queryParameters.getSelectedFields());
        val whereClauses = buildWhereClause(defaultConfig.getClauses());
        val orderByFields = queryParameters.getSelectedFields().get(queryParameters.getSortField());
        val orderByDirection = queryParameters.getSortDirection();
        val limitClause = buildLimitClause(defaultConfig.getPaging(), queryParameters.getOffset(), queryParameters.getPagingPacket());
        return SqlHelperResult.builder().selectedFields(selectedFields)
                .whereClauses(whereClauses)
                .orderByFields(orderByFields)
                .orderByDirection(orderByDirection)
                .limitClause(limitClause).build();
    }

    @Override
    public SqlHelperResult buildSingleItem(QueryParameters queryParameters, Nomenclature defaultConfig) {
        val selectedFields = buildSelectedFields(queryParameters.getSelectedFields());
        val whereClauses = buildWhereClause(defaultConfig.getClauses());
        return SqlHelperResult.builder().selectedFields(selectedFields)
                .whereClauses(whereClauses)
                .build();

    }

    String buildSelectedFields(Map<String, String> selectedFields) {
        return String.join(", ", extractSelectedFieldsValuesFromMap(selectedFields));
    }

    String buildLimitClause(Paging paging, String offset, String pagingPacket) {
        return paging.isEnabled() ? String.format("limit %s, %s", offset, pagingPacket) : "";
    }

    String buildWhereClause(List<Clause> clauses) {
        return clauses.isEmpty() ? DEFAULT_WHERE_CLAUSE : String.join(" and ", buildVaraibleInValuesStatement(clauses));
    }

    List<String> buildVaraibleInValuesStatement(List<Clause> clauses) {
        // TODO: 05/07/2018 review the code
        if (clauses.isEmpty()) throw new SqlWhereClauseFormatInvalidException("code", "The clauses should not be empty");
        return clauses.stream().map(clause -> String.format("%s IN ( %s )", clause.getName(), buildSqlStringValues(clause.getValues()))).collect(Collectors.toList());
    }

    String buildSqlStringValues(List<String> values) {
        return String.format("'%s'", String.join("', '", values));
    }
}