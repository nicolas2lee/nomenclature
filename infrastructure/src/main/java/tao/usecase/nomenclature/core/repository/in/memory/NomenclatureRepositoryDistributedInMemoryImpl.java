package tao.usecase.nomenclature.core.repository.in.memory;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import tao.usecase.nomenclature.core.NomenclatureRepository;
import tao.usecase.nomenclature.core.model.Nomenclature;
import tao.usecase.nomenclature.core.model.QueryParameters;

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
    public List<Map<String, Object>> getAllItemsBySortPaging(QueryParameters queryParameters, Nomenclature defaultNomenclatureConfig) {
        return distributedInMemoryRepository.getAllItemsBySortPaging(queryParameters, defaultNomenclatureConfig);
    }

    @Override
    public Integer countAllItems(Nomenclature defaultConfig) {
        return null;
    }

    @Override
    public Map<String, Object> getItemById(Nomenclature defaultConfig, String id, QueryParameters queryParameters) {
        return null;
    }
}
