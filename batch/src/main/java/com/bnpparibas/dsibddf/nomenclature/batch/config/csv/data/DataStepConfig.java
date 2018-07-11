package com.bnpparibas.dsibddf.nomenclature.batch.config.csv.data;

import com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.in.memory.DistributedInMemoryRepository;
import lombok.val;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class DataStepConfig {
    private final StepBuilderFactory stepBuilderFactory;
    private final DistributedInMemoryRepository distributedInMemoryRepository;


    DataStepConfig(StepBuilderFactory stepBuilderFactory, DistributedInMemoryRepository distributedInMemoryRepository) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.distributedInMemoryRepository = distributedInMemoryRepository;
    }

    @Bean
    public CsvDataReader csvDataReader() {
        val fileName = "data.csv";
        return new CsvDataReader(fileName);
    }

    @Bean(value = "insertDataStep")
    public Step insertDataStep() {
        return stepBuilderFactory.get("insertDataStep")
                .<Map<String, String>, Map<String, String>>chunk(10)
                .reader(csvDataReader())
//                .processor(processor())
                .writer(igniteDataWriter())
                .build();
    }

    @Bean
    public IgniteDataWriter igniteDataWriter() {
        return new IgniteDataWriter(distributedInMemoryRepository);
    }
}
