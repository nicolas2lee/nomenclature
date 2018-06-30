package tao.usecase.nomenclature.core.service;

import tao.usecase.nomenclature.core.model.Clause;
import tao.usecase.nomenclature.core.model.Paging;

import java.util.List;

public interface SqlHelper {
    String buildLimitClause(Paging paging, String offset, String pagingPacket);

    String buildWhereClause(List<Clause> clauses);
}
