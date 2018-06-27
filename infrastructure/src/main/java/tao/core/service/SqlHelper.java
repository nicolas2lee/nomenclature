package tao.core.service;

import tao.core.model.Clause;
import tao.core.model.Paging;

import java.util.List;

public interface SqlHelper {
    String buildLimitClause(Paging paging, String offset, String pagingPacket);

    String buildWhereClause(List<Clause> clauses);
}
