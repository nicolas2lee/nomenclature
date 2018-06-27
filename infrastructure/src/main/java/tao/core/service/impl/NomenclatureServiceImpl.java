package tao.core.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tao.core.NomenclatureService;
import tao.core.model.Nomenclature;
import tao.core.model.QueryParameters;
import tao.core.repositorymapper.ItemRepositoryMapper;
import tao.core.service.SqlHelper;
import tao.resource.exception.ResourceNotFoundException;
import tao.resource.yaml.service.model.NomenclatureModel;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class NomenclatureServiceImpl implements NomenclatureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NomenclatureServiceImpl.class);
    private static final String ErrorCode = "Err.00001";
    private static Nomenclature nomenclature;
    private final ItemRepositoryMapper itemRepositoryMapper;
    private final SqlHelper sqlHelper;

    NomenclatureServiceImpl(ItemRepositoryMapper itemRepositoryMapper,
                            SqlHelper sqlHelper) {
        this.itemRepositoryMapper = itemRepositoryMapper;
        this.sqlHelper = sqlHelper;
    }

    @Override
    public Optional<Nomenclature> getDefaultNomenclatureConfig(final String name) {
        if (nomenclature == null || !nomenclature.getResourceName().equals(name)) {
            final String ymlFilePath = String.format("nomenclatures/%s.yml", name );
            LOGGER.info(String.format("Loading yam file : %s", ymlFilePath));
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            try {
                final File file = new File(getClass().getClassLoader().getResource(ymlFilePath).getFile());
                nomenclature = mapper.readValue(file, NomenclatureModel.class).toDomain();
                if (!nomenclature.isEnabled()) return Optional.empty();
            } catch (Exception e) {
                throw new ResourceNotFoundException(ErrorCode, String.format("Mapping Error with [%s] resource: %s", name, e.getMessage()));
            }
        }
        return Optional.of(nomenclature);
    }

    @Override
    public List<Map<String, Object>> getAllItems(final QueryParameters queryParameters, final Nomenclature defaultNomenclatureConfig) {
        String selectedFields = String.join(", ", queryParameters.getSelectedFields().keySet());
        String whereClauses = sqlHelper.buildWhereClause(defaultNomenclatureConfig.getClauses());
        String orderByFields = queryParameters.getSelectedFields().get(queryParameters.getSortField());
        String orderByDirection = queryParameters.getSortDirection();
        String limitClause = sqlHelper.buildLimitClause(defaultNomenclatureConfig.getPaging(), queryParameters.getOffset(), queryParameters.getPagingPacket());
        LOGGER.info(String.format("SELECT %s FROM %s WHERE %s order by %s %s %s", selectedFields, defaultNomenclatureConfig.getDatabaseTable(),
                whereClauses, orderByFields, orderByDirection, limitClause));
        return itemRepositoryMapper.getAll(selectedFields, defaultNomenclatureConfig.getDatabaseTable(), whereClauses, orderByFields, orderByDirection, limitClause);
    }

}
