package com.bnpparibas.dsibddf.nomenclature.application.usecase.core.e2e;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = "com.bnpparibas.dsibddf.nomenclature",
        features = { "classpath:features/nomencalture.feature" },
        plugin ={"pretty",
                "html:target/cucumber-report/",
                "json:target/cucumber-report/cucumber.json"})
public class CucumberTest {
}
