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

    @Override
    public void write(List<? extends Map<String, String>> list) throws Exception {
        LOGGER.info(String.format("start creating table in ignite memory"));
/*        val finalSqlString = buildCreateTableSqlString(list);
        if (finalSqlString.isPresent()){
            LOGGER.info(String.format("The below sql statement will be executed: %s",  finalSqlString.get()));
//            distributedInMemoryRepository.insertDataFromString(finalSqlString.get());
            return;
        }*/
//        throw new CreateTableSqlEmptyException();
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
        val list = map.entrySet().stream().map(x -> String.format("%s %s", x.getKey().trim(), x.getValue().trim()))
                .collect(Collectors.toList());
        return String.join(",", list);
    }

    Optional<String> buildCreateTableSqlString(List<? extends Map<String, String>> list) {
        return list.stream()
                .map(buildSingleCreateTabelSqlString())
                .peek(LOGGER::debug)
                .reduce(String::concat);
    }
}
