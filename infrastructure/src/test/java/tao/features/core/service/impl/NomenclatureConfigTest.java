package tao.features.core.service.impl;

import org.junit.Test;
import tao.features.core.model.Nomenclature;
import tao.resource.exception.ResourceNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class NomenclatureConfigTest {

    @Test(expected = ResourceNotFoundException.class)
    public void should_throw_ResouceNotFoundException_when_nomenclature_name_is_null() {
        NomenclatureConfig.getDefaultConfig(null);
    }


    @Test(expected = ResourceNotFoundException.class)
    public void should_throw_ResouceNotFoundException_when_yaml_file_with_nomenclature_name_is_not_found() {
        NomenclatureConfig.getDefaultConfig("hello");
    }

    @Test
    public void should_return_optional_empty_when_nomenclature_is_not_enabled() {
        Optional<Nomenclature> result = NomenclatureConfig.getDefaultConfig("pays_not_enabled");

        assertThat(result).isEqualTo(Optional.empty());
    }

    @Test
    public void should_return_nomencalture_when_nomenclature_is_enabled() {
        Optional<Nomenclature> result = NomenclatureConfig.getDefaultConfig("pays_enabled");

        assertThat(result).isNotEqualTo(Optional.empty());
        assertThat(result.get().isEnabled()).isTrue();
    }
}