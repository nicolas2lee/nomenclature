package tao.features.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tao.features.core.NomenclatureService;
import tao.features.core.model.Nomenclature;
import tao.features.core.model.QueryParameters;
import tao.features.core.repositorymapper.ItemRepositoryMapper;
import tao.features.core.service.SqlHelper;

import java.util.List;
import java.util.Map;

@Service
public class NomenclatureServiceImpl implements NomenclatureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NomenclatureServiceImpl.class);
    private final ItemRepositoryMapper itemRepositoryMapper;
    private final SqlHelper sqlHelper;

    NomenclatureServiceImpl(ItemRepositoryMapper itemRepositoryMapper,
                            SqlHelper sqlHelper) {
        this.itemRepositoryMapper = itemRepositoryMapper;
        this.sqlHelper = sqlHelper;
    }


    @Override
    public List<Map<String, Object>> getAllItemsBySortPaging(final QueryParameters queryParameters, final Nomenclature defaultNomenclatureConfig) {
        final String selectedFields = String.join(", ", queryParameters.getSelectedFields().keySet());
        final String whereClauses = sqlHelper.buildWhereClause(defaultNomenclatureConfig.getClauses());
        final String orderByFields = queryParameters.getSelectedFields().get(queryParameters.getSortField());
        final String orderByDirection = queryParameters.getSortDirection();
        final String limitClause = sqlHelper.buildLimitClause(defaultNomenclatureConfig.getPaging(), queryParameters.getOffset(), queryParameters.getPagingPacket());
        LOGGER.info(String.format("SELECT %s FROM %s WHERE %s order by %s %s %s", selectedFields, defaultNomenclatureConfig.getDatabaseTable(),
                whereClauses, orderByFields, orderByDirection, limitClause));
        return itemRepositoryMapper.getAll(selectedFields, defaultNomenclatureConfig.getDatabaseTable(), whereClauses, orderByFields, orderByDirection, limitClause);
    }

    @Override
    public Integer countAllItems(Nomenclature defaultConfig) {
        return itemRepositoryMapper.count(defaultConfig.getDatabaseTable());
    }

    @Override
    public Map<String, Object> getItemById(Nomenclature defaultConfig, String id, QueryParameters queryParameters) {
        final String selectedFields = String.join(", ", queryParameters.getSelectedFields().keySet());
        final String whereClauses = sqlHelper.buildWhereClause(defaultConfig.getClauses());
        LOGGER.info(String.format("SELECT %s FROM %s WHERE %s and %s = %s ", selectedFields, defaultConfig.getDatabaseTable(),
                whereClauses, defaultConfig.getPrimaryKey(), id));
        return itemRepositoryMapper.findById(selectedFields, defaultConfig.getDatabaseTable(), whereClauses, defaultConfig.getPrimaryKey(), id);
    }

}
