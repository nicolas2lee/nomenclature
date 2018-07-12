package com.bnpparibas.dsibddf.nomenclature.batch.config.csv.data;

import com.bnpparibas.dsibddf.nomenclature.batch.config.csv.common.ListPair;
import com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.in.memory.DistributedInMemoryRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Profile("inmemory")
class IgniteDataWriter implements ItemWriter<Map<String, String>> {

    private final static String ENABLE_BULK_INSERT = "SET STREAMING ON;";
    private final static String TABLE_NAME = "pays_ISO";

    private final DistributedInMemoryRepository distributedInMemoryRepository;

    IgniteDataWriter(DistributedInMemoryRepository distributedInMemoryRepository) {
        this.distributedInMemoryRepository = distributedInMemoryRepository;
    }

    @Override
    public void write(List<? extends Map<String, String>> list) throws Exception {
        LOGGER.info("Start write csv data into ignite memory");
        val sqlScriptString = buildSqlBulkInsertString(list);
        distributedInMemoryRepository.insertDataFromString(sqlScriptString);
    }

    String buildSqlBulkInsertString(List<? extends Map<String, String>> list) {
        val stringOptional = list.stream()
                .map(buildKeyValuePair())
                .map(buildSingleInsertSql())
                .reduce(String::concat);
        if (stringOptional.isPresent()) return ENABLE_BULK_INSERT.concat(stringOptional.get());
        throw new RuntimeException();
    }

    Function<ListPair, String> buildSingleInsertSql() {
        return e -> String.format("insert into %s ( %s ) values ( %s );", TABLE_NAME,
                e.keySetToSqlExpression(), e.valueListToSqlExpression());
    }

    Function<Map<String, String>, ListPair> buildKeyValuePair() {
        return map -> new ListPair(map.keySet(), map.values());
    }


}
