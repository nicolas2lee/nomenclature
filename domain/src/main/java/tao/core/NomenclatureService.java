package tao.core;

import tao.core.model.Nomenclature;

import java.util.Optional;

public interface NomenclatureService {
    Optional<Nomenclature> getNomenclature(final String name);
}
