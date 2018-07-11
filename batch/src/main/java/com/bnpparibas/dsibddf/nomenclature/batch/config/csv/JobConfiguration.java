package com.bnpparibas.dsibddf.nomenclature.batch.config.csv;

import com.bnpparibas.dsibddf.nomenclature.batch.config.csv.data.DataStepConfig;
import com.bnpparibas.dsibddf.nomenclature.batch.config.csv.table.TableStepConfig;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({TableStepConfig.class, DataStepConfig.class})
public class JobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;

    //@Resource(name = "createTableStep")
    @Autowired
    @Qualifier(value = "createTableStep")
    private Step createTableStep;

    @Autowired
    @Qualifier(value = "insertDataStep")
    //@Resource(name = "insertDataStep")
    private Step insertDataStep;

    JobConfiguration(JobBuilderFactory jobBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean
    public Job importDataFromCsvJob() {
        return jobBuilderFactory.get("importDataFromCsvJob")
                .incrementer(new RunIdIncrementer())
                .flow(createTableStep)
                .next(insertDataStep)
                .end()
                .build();
    }

}
