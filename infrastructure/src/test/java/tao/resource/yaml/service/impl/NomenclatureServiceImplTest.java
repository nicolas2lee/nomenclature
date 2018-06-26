package tao.resource.yaml.service.impl;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tao.core.model.Nomenclature;
import tao.resource.exception.ResourceNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class NomenclatureServiceImplTest {

    private NomenclatureServiceImpl nomenclatureService;

    @Before
    public void setUp() throws Exception {
        nomenclatureService = new NomenclatureServiceImpl();
    }

    @Test(expected = ResourceNotFoundException.class)
    public void should_throw_ResouceNotFoundException_when_nomenclature_name_is_null() {
        Optional<Nomenclature> result = nomenclatureService.getDefaultNomenclatureConfig(null);
    }


    @Test(expected = ResourceNotFoundException.class)
    public void should_throw_ResouceNotFoundException_when_yaml_file_with_nomenclature_name_is_not_found() {
        Optional<Nomenclature> result = nomenclatureService.getDefaultNomenclatureConfig("hello");
    }

    @Test
    public void should_return_optional_empty_when_nomenclature_is_not_enabled() {
        Optional<Nomenclature> result = nomenclatureService.getDefaultNomenclatureConfig("pays_not_enabled");

        assertThat(result).isEqualTo(Optional.empty());
    }


    @Test
    public void should_return_nomencalture_when_nomenclature_is_enabled() {
        Optional<Nomenclature> result = nomenclatureService.getDefaultNomenclatureConfig("pays_enabled");

        assertThat(result).isNotEqualTo(Optional.empty());
        assertThat(result.get().isEnabled()).isTrue();
    }
}