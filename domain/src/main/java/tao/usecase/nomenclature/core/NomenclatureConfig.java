package tao.usecase.nomenclature.core;

import tao.usecase.nomenclature.core.model.Nomenclature;

public interface NomenclatureConfig {
    Nomenclature getDefaultConfig(final String name);
}
