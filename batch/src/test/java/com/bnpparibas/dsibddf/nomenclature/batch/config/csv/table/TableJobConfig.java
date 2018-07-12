package com.bnpparibas.dsibddf.nomenclature.batch.config.csv.table;

import com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.in.memory.DistributedInMemoryRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;

@Configuration
@Import(TableStepConfig.class)
@EnableBatchProcessing
class TableJobConfig {

    private final JobBuilderFactory jobBuilderFactory;

    @MockBean
    private DistributedInMemoryRepository distributedInMemoryRepository;

    @Resource(name = "createTableStep")
    private Step step;

    TableJobConfig(JobBuilderFactory jobBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean
    public Job testTableJob() {
        return jobBuilderFactory.get("testTableJob")
                .incrementer(new RunIdIncrementer())
                .flow(step)
                .end()
                .build();
    }

    @Bean
    public JobLauncherTestUtils jobLauncherTestUtils() {
        return new JobLauncherTestUtils();
    }

/*    @Bean
    public JobRepository jobRepository() throws Exception {
        MapJobExplorerFactoryBean factoryBean = new MapJobExplorerFactoryBean();
        return (JobRepository) factoryBean.getObject();
    }

    @Bean
    public JobLauncher jobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository());
        return jobLauncher;
    }*/
}
