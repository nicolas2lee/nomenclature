package com.bnpparibas.dsibddf.nomenclature.infrastructure.core.service.impl;

import com.bnpparibas.dsibddf.nomenclature.domain.core.NomenclatureConfig;
import com.bnpparibas.dsibddf.nomenclature.domain.core.model.Nomenclature;
import com.bnpparibas.dsibddf.nomenclature.infrastructure.resource.exception.ResourceNotFoundException;
import com.bnpparibas.dsibddf.nomenclature.infrastructure.resource.yaml.service.model.NomenclatureModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;

@Slf4j
@Service
public class NomenclatureConfigImpl implements NomenclatureConfig {

    private static final String ErrorCode = "Err.00001";

    private static Nomenclature nomenclature;

    NomenclatureConfigImpl() {
    }

    Optional<Nomenclature> getOptioanlDefaultConfig(final String name) {
        if (nomenclature == null || !nomenclature.getResourceName().equals(name)) {
            val ymlFilePath = String.format("nomenclatures/%s.yml", name);
            LOGGER.info(String.format("Loading yam file : %s", ymlFilePath));
            val mapper = new ObjectMapper(new YAMLFactory());
            try {
                val file = new File(NomenclatureConfigImpl.class.getClassLoader().getResource(ymlFilePath).getFile());
                nomenclature = mapper.readValue(file, NomenclatureModel.class).toDomain();
                if (!nomenclature.isEnabled()) return Optional.empty();
            } catch (Exception e) {
                throw new ResourceNotFoundException(ErrorCode, String.format("Mapping Error with [%s] resource: %s", name, e.getMessage()));
            }
        }
        return Optional.of(nomenclature);

    }

    @Override
    public Nomenclature getDefaultConfig(String name) {
        return getOptioanlDefaultConfig(name).orElse(Nomenclature.NONE);
    }
}