package com.bnpparibas.dsibddf.nomenclature.infrastructure.core.service.impl;

import com.bnpparibas.dsibddf.nomenclature.infrastructure.resource.exception.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import com.bnpparibas.dsibddf.nomenclature.domain.core.model.Nomenclature;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class NomenclatureConfigImplTest {
    private NomenclatureConfigImpl nomenclatureConfigImpl;

    @Before
    public void setUp() throws Exception {
        nomenclatureConfigImpl = new NomenclatureConfigImpl();
    }

    @Test(expected = ResourceNotFoundException.class)
    public void should_throw_ResouceNotFoundException_when_nomenclature_name_is_null() {
        nomenclatureConfigImpl.getOptioanlDefaultConfig(null);
    }


    @Test(expected = ResourceNotFoundException.class)
    public void should_throw_ResouceNotFoundException_when_yaml_file_with_nomenclature_name_is_not_found() {
        nomenclatureConfigImpl.getOptioanlDefaultConfig("hello");
    }

    @Test
    public void should_return_optional_empty_when_nomenclature_is_not_enabled() {
        Optional<Nomenclature> result = nomenclatureConfigImpl.getOptioanlDefaultConfig("pays_not_enabled");

        assertThat(result).isEqualTo(Optional.empty());
    }

    @Test
    public void should_return_nomenclature_none_when_nomenclature_is_not_enabled() {
        Nomenclature result = nomenclatureConfigImpl.getDefaultConfig("pays_not_enabled");

        assertThat(result).isEqualTo(Nomenclature.NONE);
    }

    @Test
    public void should_return_nomencalture_when_nomenclature_is_enabled() {
        Optional<Nomenclature> result = nomenclatureConfigImpl.getOptioanlDefaultConfig("pays_enabled");

        assertThat(result).isNotEqualTo(Optional.empty());
        assertThat(result.get().isEnabled()).isTrue();
    }
}