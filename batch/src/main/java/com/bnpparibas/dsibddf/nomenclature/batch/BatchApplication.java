package com.bnpparibas.dsibddf.nomenclature.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan(basePackages = {"com.bnpparibas.dsibddf.nomenclature.application",
        "com.bnpparibas.dsibddf.nomenclature.domain",
        "com.bnpparibas.dsibddf.nomenclature.infrastructure",
        "com.bnpparibas.dsibddf.nomenclature.batch"})
public class BatchApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(BatchApplication.class, args);
    }
}
