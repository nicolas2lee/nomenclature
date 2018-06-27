package tao.features.core.service;

import tao.features.core.model.Clause;
import tao.features.core.model.Paging;

import java.util.List;

public interface SqlHelper {
    String buildLimitClause(Paging paging, String offset, String pagingPacket);

    String buildWhereClause(List<Clause> clauses);
}
