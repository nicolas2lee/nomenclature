package com.bnpparibas.dsibddf.nomenclature.exposition;


import lombok.val;
import org.junit.Test;
import org.springframework.boot.SpringApplication;

public class InmemoryProfileApplicationITTest {

    @Test
    public void should_load_application_context() {
        SpringApplication app = new SpringApplication(Application.class);
        app.setAdditionalProfiles("inmemory");
        val ctx = app.run();
        ctx.close();

    }
}