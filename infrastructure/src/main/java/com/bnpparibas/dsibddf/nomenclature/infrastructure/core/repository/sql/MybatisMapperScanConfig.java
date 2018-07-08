package com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.sql;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!inmemory")
@MapperScan("com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.sql.mapper")
public class MybatisMapperScanConfig {
}
