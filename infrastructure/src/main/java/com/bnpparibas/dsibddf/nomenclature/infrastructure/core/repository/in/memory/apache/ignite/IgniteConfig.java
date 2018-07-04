package com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.in.memory.apache.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("inmemory")
@Configuration
public class IgniteConfig {
    @Bean
    public Ignite igniteInstance() {
        return Ignition.start("config/example-ignite.xml");
    }

}
