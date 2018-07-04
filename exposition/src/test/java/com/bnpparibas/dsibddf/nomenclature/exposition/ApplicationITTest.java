package com.bnpparibas.dsibddf.nomenclature.exposition;

import lombok.val;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

public class ApplicationITTest {

    @Test
    public void should_load_application_context() {
        SpringApplication app = new SpringApplication(Application.class);
        val ctx = app.run();
        ctx.close();
    }
}
