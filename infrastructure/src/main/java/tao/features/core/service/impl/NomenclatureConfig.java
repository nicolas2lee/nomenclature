package tao.features.core.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tao.features.core.model.Nomenclature;
import tao.resource.exception.ResourceNotFoundException;
import tao.resource.yaml.service.model.NomenclatureModel;

import java.io.File;
import java.util.Optional;

@Service
public class NomenclatureConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(NomenclatureConfig.class);

    private static final String ErrorCode = "Err.00001";

    private static Nomenclature nomenclature;

    private NomenclatureConfig() {
    }

    public static Optional<Nomenclature> getDefaultConfig(final String name) {
        if (nomenclature == null || !nomenclature.getResourceName().equals(name)) {
            synchronized (NomenclatureConfig.class) {
                if (nomenclature == null || !nomenclature.getResourceName().equals(name)) {
                    final String ymlFilePath = String.format("nomenclatures/%s.yml", name);
                    LOGGER.info(String.format("Loading yam file : %s", ymlFilePath));
                    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                    try {
                        final File file = new File(NomenclatureConfig.class.getClassLoader().getResource(ymlFilePath).getFile());
                        nomenclature = mapper.readValue(file, NomenclatureModel.class).toDomain();
                        if (!nomenclature.isEnabled()) return Optional.empty();
                    } catch (Exception e) {
                        throw new ResourceNotFoundException(ErrorCode, String.format("Mapping Error with [%s] resource: %s", name, e.getMessage()));
                    }
                    return Optional.of(nomenclature);
                }
            }
        }
        return Optional.of(nomenclature);

    }
}