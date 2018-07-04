package com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.in.memory;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import com.bnpparibas.dsibddf.nomenclature.domain.core.NomenclatureRepository;
import com.bnpparibas.dsibddf.nomenclature.domain.core.model.Nomenclature;
import com.bnpparibas.dsibddf.nomenclature.domain.core.model.QueryParameters;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Profile("inmemory")
@Service("nomenclatureRepository")
class NomenclatureRepositoryDistributedInMemoryImpl implements NomenclatureRepository {

    private final DistributedInMemoryRepository distributedInMemoryRepository;

    NomenclatureRepositoryDistributedInMemoryImpl(DistributedInMemoryRepository distributedInMemoryRepository){
        this.distributedInMemoryRepository = distributedInMemoryRepository;
    }

    @Override
    public List<Map<String, Object>> getAllItemsBySortPaging(QueryParameters queryParameters, Nomenclature defaultNomenclatureConfig) throws SQLException {
        return distributedInMemoryRepository.getAllItemsBySortPaging(queryParameters, defaultNomenclatureConfig);
    }

    @Override
    public Integer countAllItems(Nomenclature defaultConfig) throws SQLException {
        return distributedInMemoryRepository.countAllItems(defaultConfig);
    }

    @Override
    public Map<String, Object> getItemById(Nomenclature defaultConfig, String id, QueryParameters queryParameters) throws SQLException {
        return distributedInMemoryRepository.getItemById(defaultConfig, id, queryParameters);
    }
}
