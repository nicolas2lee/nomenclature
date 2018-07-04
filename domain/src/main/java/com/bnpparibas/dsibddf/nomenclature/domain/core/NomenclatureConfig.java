package com.bnpparibas.dsibddf.nomenclature.domain.core;

import com.bnpparibas.dsibddf.nomenclature.domain.core.model.Nomenclature;

public interface NomenclatureConfig {
    Nomenclature getDefaultConfig(final String name);
}
