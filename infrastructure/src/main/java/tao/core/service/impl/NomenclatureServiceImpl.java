package tao.core.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tao.core.NomenclatureService;
import tao.core.mapper.ItemMapper;
import tao.core.model.Clause;
import tao.core.model.Nomenclature;
import tao.core.model.Paging;
import tao.core.model.QueryParameters;
import tao.core.service.exception.SqlWhereClauseFormatInvalidException;
import tao.resource.exception.ResourceNotFoundException;
import tao.resource.yaml.service.model.NomenclatureModel;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NomenclatureServiceImpl implements NomenclatureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NomenclatureServiceImpl.class);
    private static final String ErrorCode = "Err.00001";
    private static Nomenclature nomenclature;
    private final ItemMapper itemMapper;

    NomenclatureServiceImpl(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    @Override
    public Optional<Nomenclature> getDefaultNomenclatureConfig(final String name) {
        if (nomenclature == null || !nomenclature.getResourceName().equals(name)) {
            final String ymlFilePath = String.format("nomenclatures/%s.yml", name );
            LOGGER.info(String.format("Loading yam file : %s", ymlFilePath));
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            try {
                final File file = new File(getClass().getClassLoader().getResource(ymlFilePath).getFile());
                nomenclature = mapper.readValue(file, NomenclatureModel.class).toDomainObject();
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
        String whereClauses = buildWhereClause(defaultNomenclatureConfig.getClause());
        String orderByFields = queryParameters.getSelectedFields().get(queryParameters.getSortField());
        String orderByDirection = queryParameters.getSortDirection();
        String limitClause = buildLimitClause(defaultNomenclatureConfig.getPaging(), queryParameters.getOffset(), queryParameters.getPagingPacket());
        return itemMapper.getAll(selectedFields, defaultNomenclatureConfig.getDatabaseTable(), whereClauses, orderByFields, orderByDirection, limitClause);
    }

    String buildLimitClause(Paging paging, String offset, String pagingPacket) {
        return paging.isEnabled() ? String.format("limit %s, %s", offset, pagingPacket) : "";
    }

    String buildWhereClause(List<Clause> clauses) {
        return clauses.isEmpty() ? "1=1" : String.join(" and ", buildVaraibleInValuesStatement(clauses));
    }

    List<String> buildVaraibleInValuesStatement(List<Clause> clauses) {
        if (clauses.isEmpty()) throw new SqlWhereClauseFormatInvalidException("The clauses should not be empty");
        return clauses.stream().map(clause -> String.format("%s IN ( %s )", clause.getName(), buildSqlStringValues(clause.getValues()))).collect(Collectors.toList());
    }

    String buildSqlStringValues(String[] values) {
        return String.format("'%s'", String.join("', '", values));
    }
}
