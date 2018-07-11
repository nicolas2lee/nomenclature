package com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.in.memory;

import com.bnpparibas.dsibddf.nomenclature.domain.core.NomenclatureRepository;

import java.sql.SQLException;

public interface DistributedInMemoryRepository extends NomenclatureRepository {
    void insertDataFromString(final String s) throws SQLException, ClassNotFoundException;
}
