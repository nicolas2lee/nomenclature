package com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.sql;

import com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.sql.mapper.ItemRepositoryMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import com.bnpparibas.dsibddf.nomenclature.domain.core.NomenclatureRepository;
import com.bnpparibas.dsibddf.nomenclature.domain.core.model.Nomenclature;
import com.bnpparibas.dsibddf.nomenclature.domain.core.model.QueryParameters;

import javax.inject.Provider;
import java.util.List;
import java.util.Map;

@Profile("!inmemory")
@Service("nomenclatureRepository")
@Slf4j
public class NomenclatureRepositorySqlImpl implements NomenclatureRepository {

    private final ItemRepositoryMapper itemRepositoryMapper;

    private final Provider<SqlHelper> sqlHelperProvider;

    NomenclatureRepositorySqlImpl(ItemRepositoryMapper itemRepositoryMapper,
                                  Provider<SqlHelper> sqlHelperProvider) {
        this.itemRepositoryMapper = itemRepositoryMapper;
        this.sqlHelperProvider = sqlHelperProvider;
    }


    @Override
    public List<Map<String, Object>> getAllItemsBySortPaging(final QueryParameters queryParameters, final Nomenclature defaultConfig) {
        val sqlHelper = sqlHelperProvider.get();
        val result = sqlHelper.buildAllItemsBySortPaging(queryParameters, defaultConfig);
        LOGGER.info(String.format("SELECT %s FROM %s WHERE %s order by %s %s %s", result.getSelectedFields(), defaultConfig.getDatabaseTable(),
                result.getWhereClauses(), result.getOrderByFields(), result.getOrderByDirection(), result.getLimitClause()));
        return itemRepositoryMapper.getAll(result.getSelectedFields(), defaultConfig.getDatabaseTable(),
                result.getWhereClauses(), result.getOrderByFields(), result.getOrderByDirection(), result.getLimitClause());
    }

    @Override
    public Integer countAllItems(Nomenclature defaultConfig) {
        return itemRepositoryMapper.count(defaultConfig.getDatabaseTable());
    }

    @Override
    public Map<String, Object> getItemById(Nomenclature defaultConfig, String id, QueryParameters queryParameters) {
        val sqlHelper = sqlHelperProvider.get();
        val result = sqlHelper.buildSingleItem(queryParameters, defaultConfig);
        LOGGER.info(String.format("SELECT %s FROM %s WHERE %s and %s = %s ", result.getSelectedFields(), defaultConfig.getDatabaseTable(),
                result.getWhereClauses(), defaultConfig.getPrimaryKey(), id));
        return itemRepositoryMapper.findById(result.getSelectedFields(), defaultConfig.getDatabaseTable(), result.getWhereClauses(), defaultConfig.getPrimaryKey(), id);
    }

}
