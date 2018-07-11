package com.bnpparibas.dsibddf.nomenclature.batch.config.csv.data;

import com.bnpparibas.dsibddf.nomenclature.batch.config.csv.common.ListPair;
import com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.in.memory.DistributedInMemoryRepository;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class IgniteDataWriterTest {

    private IgniteDataWriter igniteDataWriter;

    @Mock
    private DistributedInMemoryRepository distributedInMemoryRepository;

    @Before
    public void setUp() throws Exception {
        igniteDataWriter = new IgniteDataWriter(distributedInMemoryRepository);
    }

    @Test
    public void should_return_insert_sql_string() {
        val pair = new ListPair(Stream.of("key1", "key2").collect(Collectors.toSet()),
                Arrays.asList("value1", "value2"));
        val expected = "insert into pays_ISO ( key1,key2 ) values ( 'value1','value2' );";

        val result = igniteDataWriter.buildSingleInsertSql().apply(pair);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void should_return_bulk_insert_string_when_map_has_more_than_two_elements() {
        Map<String, String> map1 = new HashMap<>();
        map1.put("key11", "value11");
        Map<String, String> map2 = new HashMap<>();
        map2.put("key21", "value21");
        val expected = "SET STREAMING ON;insert into pays_ISO ( key11 ) values ( 'value11' );insert into pays_ISO ( key21 ) values ( 'value21' );";

        val result = igniteDataWriter.buildSqlBulkInsertString(Arrays.asList(map1, map2));

        assertThat(result).isEqualTo(expected);
    }
}