package tao.usecase.nomenclature.core.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import tao.usecase.nomenclature.core.NomenclatureConfig;
import tao.usecase.nomenclature.core.model.Nomenclature;
import tao.usecase.nomenclature.resource.exception.ResourceNotFoundException;
import tao.usecase.nomenclature.resource.yaml.service.model.NomenclatureModel;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.util.Optional;
@Service
public class NomenclatureConfigImpl implements NomenclatureConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(NomenclatureConfigImpl.class);

    private static final String ErrorCode = "Err.00001";

    private static Nomenclature nomenclature;

    NomenclatureConfigImpl() {
    }

    Optional<Nomenclature> getOptioanlDefaultConfig(final String name) {
        if (nomenclature == null || !nomenclature.getResourceName().equals(name)) {
            final String ymlFilePath = String.format("nomenclatures/%s.yml", name);
            LOGGER.info(String.format("Loading yam file : %s", ymlFilePath));
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            try {
                final File file = new File(NomenclatureConfigImpl.class.getClassLoader().getResource(ymlFilePath).getFile());
                nomenclature = mapper.readValue(file, NomenclatureModel.class).toDomain();
                if (!nomenclature.isEnabled()) return Optional.empty();
            } catch (Exception e) {
                throw new ResourceNotFoundException(ErrorCode, String.format("Mapping Error with [%s] resource: %s", name, e.getMessage()));
            }
            return Optional.of(nomenclature);
        }
        return Optional.of(nomenclature);

    }

    @Override
    public Nomenclature getDefaultConfig(String name) {
        return getOptioanlDefaultConfig(name).orElse(Nomenclature.NONE);
    }
}