package tao.usecase.nomenclature.core.repository.sql;

import tao.usecase.nomenclature.core.model.Clause;
import tao.usecase.nomenclature.core.model.Paging;

import java.util.List;
import java.util.Map;

public interface SqlHelper {
    String buildSelectedFields(Map<String, String> selectedFields);

    String buildLimitClause(Paging paging, String offset, String pagingPacket);

    String buildWhereClause(List<Clause> clauses);
}
