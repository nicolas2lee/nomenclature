package tao.usecase.nomenclature.core.repository.sql.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import tao.usecase.nomenclature.core.model.Clause;
import tao.usecase.nomenclature.core.model.Paging;
import tao.usecase.nomenclature.core.repository.sql.SqlHelper;
import tao.usecase.nomenclature.core.repository.sql.exception.SqlWhereClauseFormatInvalidException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Scope("prototype")
public class SqlHelperImpl implements SqlHelper {
    private static final String DEFAULT_WHERE_CLAUSE = "1=1";

    @Override
    public String buildLimitClause(Paging paging, String offset, String pagingPacket) {
        return paging.isEnabled() ? String.format("limit %s, %s", offset, pagingPacket) : "";
    }

    @Override
    public String buildWhereClause(List<Clause> clauses) {
        return clauses.isEmpty() ? DEFAULT_WHERE_CLAUSE : String.join(" and ", buildVaraibleInValuesStatement(clauses));
    }

    List<String> buildVaraibleInValuesStatement(List<Clause> clauses) {
        if (clauses.isEmpty()) throw new SqlWhereClauseFormatInvalidException("The clauses should not be empty");
        return clauses.stream().map(clause -> String.format("%s IN ( %s )", clause.getName(), buildSqlStringValues(clause.getValues()))).collect(Collectors.toList());
    }

    String buildSqlStringValues(List<String> values) {
        return String.format("'%s'", String.join("', '", values));
    }
}