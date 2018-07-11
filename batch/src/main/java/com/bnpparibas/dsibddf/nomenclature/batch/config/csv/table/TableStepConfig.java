package com.bnpparibas.dsibddf.nomenclature.batch.config.csv.table;

import com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.in.memory.DistributedInMemoryRepository;
import lombok.val;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class TableStepConfig {
    private final StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DistributedInMemoryRepository distributedInMemoryRepository;

    TableStepConfig(StepBuilderFactory stepBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
    }


    @Bean(value = "createTableStep")
    public Step createTableStep() {
        return stepBuilderFactory.get("readTableStructureFromCsv")
                .<Map<String, String>, Map<String, String>>chunk(10)
                .reader(csvTableReader())
                .writer(igniteTableWriter())
                .build();
    }

    @Bean
    public CsvTableReader csvTableReader() {
        val fileName = "table.csv";
        return new CsvTableReader(fileName);
    }

    @Bean
    public IgniteTableWriter igniteTableWriter() {
        return new IgniteTableWriter(distributedInMemoryRepository);
    }
}
