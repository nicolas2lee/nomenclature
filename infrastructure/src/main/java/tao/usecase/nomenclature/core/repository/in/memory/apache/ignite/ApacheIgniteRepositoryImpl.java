package tao.usecase.nomenclature.core.repository.in.memory.apache.ignite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import tao.usecase.nomenclature.core.model.Nomenclature;
import tao.usecase.nomenclature.core.model.QueryParameters;
import tao.usecase.nomenclature.core.repository.in.memory.DistributedInMemoryRepository;
import tao.usecase.nomenclature.core.repository.sql.SqlHelper;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository("distributedInMemoryRepository")
class ApacheIgniteRepositoryImpl implements DistributedInMemoryRepository {

    private final static Logger LOGGER = LoggerFactory.getLogger(ApacheIgniteRepositoryImpl.class);

    private final SqlHelper sqlHelper;
    private final IgniteJDBC igniteJDBC;

    ApacheIgniteRepositoryImpl(SqlHelper sqlHelper, IgniteJDBC igniteJDBC){
        this.sqlHelper = sqlHelper;
        this.igniteJDBC = igniteJDBC;
    }

    @Override
    public List<Map<String, Object>> getAllItemsBySortPaging(QueryParameters queryParameters, Nomenclature defaultNomenclatureConfig) throws SQLException {
        final String selectedFields = sqlHelper.buildSelectedFields(queryParameters.getSelectedFields());
        final String whereClauses = sqlHelper.buildWhereClause(defaultNomenclatureConfig.getClauses());
        final String orderByFields = queryParameters.getSelectedFields().get(queryParameters.getSortField());
        final String orderByDirection = queryParameters.getSortDirection();
        final String limitClause = sqlHelper.buildLimitClause(defaultNomenclatureConfig.getPaging(), queryParameters.getOffset(), queryParameters.getPagingPacket());
        final String sqlString = String.format("SELECT %s FROM %s WHERE %s order by %s %s %s", selectedFields, defaultNomenclatureConfig.getDatabaseTable(),
                whereClauses, orderByFields, orderByDirection, limitClause);
        LOGGER.info(sqlString);
        return igniteJDBC.getMultiRowData(sqlString);
    }

    @Override
    public Integer countAllItems(Nomenclature defaultConfig) throws SQLException {
        final String sqlString = String.format("SELECT count(1) FROM %s", defaultConfig.getDatabaseTable());
        return igniteJDBC.getCount(sqlString);
    }

    @Override
    public Map<String, Object> getItemById(Nomenclature defaultConfig, String id, QueryParameters queryParameters) throws SQLException {
        final String selectedFields = sqlHelper.buildSelectedFields(queryParameters.getSelectedFields());
        final String whereClauses = sqlHelper.buildWhereClause(defaultConfig.getClauses());
        final String sqlSting = String.format("SELECT %s FROM %s WHERE %s and %s = %s ", selectedFields, defaultConfig.getDatabaseTable(),
                whereClauses, defaultConfig.getPrimaryKey(), id);
        LOGGER.info(sqlSting);
        return igniteJDBC.getSingleRowData(sqlSting);
    }
}