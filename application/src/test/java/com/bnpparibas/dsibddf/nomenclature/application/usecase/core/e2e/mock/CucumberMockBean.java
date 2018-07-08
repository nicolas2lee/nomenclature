package com.bnpparibas.dsibddf.nomenclature.application.usecase.core.e2e.mock;

import com.bnpparibas.dsibddf.nomenclature.domain.core.NomenclatureConfig;
import com.bnpparibas.dsibddf.nomenclature.domain.core.NomenclatureRepository;

import static org.mockito.Mockito.mock;

public class CucumberMockBean {

    public NomenclatureConfig nomenclatureConfig(){
        return mock(NomenclatureConfig.class);
    }

    public NomenclatureRepository nomenclatureRepository(){
        return mock(NomenclatureRepository.class);
    }
}
