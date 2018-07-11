package com.bnpparibas.dsibddf.nomenclature.exposition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.bnpparibas.dsibddf.nomenclature.application",
        "com.bnpparibas.dsibddf.nomenclature.domain",
        "com.bnpparibas.dsibddf.nomenclature.infrastructure",
        "com.bnpparibas.dsibddf.nomenclature.exposition"})
//@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        /**
         * To avoid class loader memory leak, we should close spring context when undeploy or close the application,
         * registerShutdownHook is useful only when the application is deployed into jvm directly: like docker
         * for traditional deployment onto servers: tomcat, weblogic
         * should check this issue: https://github.com/spring-cloud/spring-cloud-commons/issues/211
         */
        applicationContext.registerShutdownHook();
    }
}
