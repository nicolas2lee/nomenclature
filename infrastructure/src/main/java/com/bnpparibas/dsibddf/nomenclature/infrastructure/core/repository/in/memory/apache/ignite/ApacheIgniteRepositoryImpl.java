package com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.in.memory.apache.ignite;

import com.bnpparibas.dsibddf.nomenclature.domain.core.model.Nomenclature;
import com.bnpparibas.dsibddf.nomenclature.domain.core.model.QueryParameters;
import com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.in.memory.DistributedInMemoryRepository;
import com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.sql.SqlHelper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.inject.Provider;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Profile("inmemory")
@Repository
@Slf4j
class ApacheIgniteRepositoryImpl implements DistributedInMemoryRepository {

    private final Provider<SqlHelper> sqlHelperProvider;
    private final IgniteJDBC igniteJDBC;

    ApacheIgniteRepositoryImpl(Provider<SqlHelper> sqlHelperProvider, IgniteJDBC igniteJDBC){
        this.sqlHelperProvider = sqlHelperProvider;
        this.igniteJDBC = igniteJDBC;
    }

    @Override
    public List<Map<String, Object>> getAllItemsBySortPaging(QueryParameters queryParameters, Nomenclature defaultConfig) throws SQLException, ClassNotFoundException {
        val sqlHelper = sqlHelperProvider.get();
        val result = sqlHelper.buildAllItemsBySortPaging(queryParameters, defaultConfig);
        val sqlString = String.format("SELECT %s FROM %s WHERE %s order by %s %s %s", result.getSelectedFields(), defaultConfig.getDatabaseTable(),
                result.getWhereClauses(), result.getOrderByFields(), result.getOrderByDirection(), result.getLimitClause());
        LOGGER.info(sqlString);
        return igniteJDBC.getMultiRowData(sqlString);
    }

    @Override
    public Integer countAllItems(Nomenclature defaultConfig) throws SQLException, ClassNotFoundException {
        val sqlString = String.format("SELECT count(1) FROM %s", defaultConfig.getDatabaseTable());
        LOGGER.info(sqlString);
        return igniteJDBC.getCount(sqlString);
    }

    @Override
    public Map<String, Object> getItemById(Nomenclature defaultConfig, String id, QueryParameters queryParameters) throws SQLException, ClassNotFoundException {
        val sqlHelper = sqlHelperProvider.get();
        val result = sqlHelper.buildSingleItem(queryParameters, defaultConfig);
        val sqlSting = String.format("SELECT %s FROM %s WHERE %s and %s = %s ", result.getSelectedFields(), defaultConfig.getDatabaseTable(),
                result.getWhereClauses(), defaultConfig.getPrimaryKey(), id);
        LOGGER.info(sqlSting);
        return igniteJDBC.getSingleRowData(sqlSting);
    }

    @Override
    public void insertDataFromString(String s) throws SQLException, ClassNotFoundException {
        igniteJDBC.insertDataFromString(s);
    }
}
