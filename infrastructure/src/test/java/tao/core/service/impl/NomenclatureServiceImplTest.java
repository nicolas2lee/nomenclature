package tao.core.service.impl;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import tao.core.mapper.ItemMapper;
import tao.core.model.Nomenclature;
import tao.resource.exception.ResourceNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class NomenclatureServiceImplTest {

    private NomenclatureServiceImpl nomenclatureService;

    @Mock
    ItemMapper itemMapper;

    @Before
    public void setUp() throws Exception {
        nomenclatureService = new NomenclatureServiceImpl(itemMapper);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void should_throw_ResouceNotFoundException_when_nomenclature_name_is_null() {
        nomenclatureService.getDefaultNomenclatureConfig(null);
    }


    @Test(expected = ResourceNotFoundException.class)
    public void should_throw_ResouceNotFoundException_when_yaml_file_with_nomenclature_name_is_not_found() {
        nomenclatureService.getDefaultNomenclatureConfig("hello");
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