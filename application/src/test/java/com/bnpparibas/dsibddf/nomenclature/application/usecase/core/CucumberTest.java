package com.bnpparibas.dsibddf.nomenclature.application.usecase.core;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = "com.bnpparibas.dsibddf.nomenclature.application",
        features = { "classpath:features/nomencalture.feature" })
public class CucumberTest {
}
