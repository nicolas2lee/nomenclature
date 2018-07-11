package com.bnpparibas.dsibddf.nomenclature.batch.config.csv.table;

import com.bnpparibas.dsibddf.nomenclature.batch.config.csv.TablenameNotFoundException;
import com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.in.memory.DistributedInMemoryRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.batch.item.ItemWriter;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


@Slf4j
class IgniteTableWriter implements ItemWriter<Map<String, String>> {

    private final static String TABLE_NAME_KEY = "table_name";

    private final DistributedInMemoryRepository distributedInMemoryRepository;

    IgniteTableWriter(DistributedInMemoryRepository distributedInMemoryRepository) {
        this.distributedInMemoryRepository = distributedInMemoryRepository;
    }


    private Function<Map<String, String>, String> buildSingleCreateTabelSqlString() {
        return map -> {
            val tableNameOptional = Optional.ofNullable(map.get(TABLE_NAME_KEY));
            if (!tableNameOptional.isPresent()) throw new TablenameNotFoundException();
            map.remove(TABLE_NAME_KEY);
            val tableName = tableNameOptional.get();
            val s = buildSingleCreateTabelSqlString(map);
            return String.format("drop table if exists %s; create table %s (%s);", tableName, tableName, s);
        };
    }

    private String buildSingleCreateTabelSqlString(Map<String, String> map) {
        val list = map.entrySet().stream().map(x -> String.format("%s %s", x.getKey(), x.getValue()))
                .collect(Collectors.toList());
        return String.join(",", list);
    }

    @Override
    public void write(List<? extends Map<String, String>> list) throws Exception {
        val finalSqlString = buildCreateTableSqlString(list);
        if (finalSqlString.isPresent())
            distributedInMemoryRepository.insertDataFromString(finalSqlString.get());
        throw new CreateTableSqlEmptyException();
    }

    Optional<String> buildCreateTableSqlString(List<? extends Map<String, String>> list) {
        val finalSqlString = list.stream()
                .map(buildSingleCreateTabelSqlString())
                .peek(LOGGER::debug)
                .reduce(String::concat);
        LOGGER.info(String.format("The below script will be executed, and add to ignite memory: %s", finalSqlString));
        return finalSqlString;
    }
}
