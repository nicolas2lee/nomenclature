package com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.sql.impl;

import com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.sql.SqlHelper;
import com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.sql.exception.SqlWhereClauseFormatInvalidException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.bnpparibas.dsibddf.nomenclature.domain.core.model.Clause;
import com.bnpparibas.dsibddf.nomenclature.domain.core.model.Paging;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Scope("prototype")
public class SqlHelperImpl implements SqlHelper {
    private static final String DEFAULT_WHERE_CLAUSE = "1=1";

    private Set<String> extractSelectedFieldsValuesFromMap(Map<String, String> selectedFields) {
        return selectedFields.entrySet().stream().map(x -> x.getValue()).collect(Collectors.toSet());
    }

    @Override
    public String buildSelectedFields(Map<String, String> selectedFields) {
        return String.join(", ", extractSelectedFieldsValuesFromMap(selectedFields));
    }

    @Override
    public String buildLimitClause(Paging paging, String offset, String pagingPacket) {
        return paging.isEnabled() ? String.format("limit %s, %s", offset, pagingPacket) : "";
    }

    @Override
    public String buildWhereClause(List<Clause> clauses) {
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