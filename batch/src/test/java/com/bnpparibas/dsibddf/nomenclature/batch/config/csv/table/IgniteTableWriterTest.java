package com.bnpparibas.dsibddf.nomenclature.batch.config.csv.table;

import com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.in.memory.DistributedInMemoryRepository;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class IgniteTableWriterTest {

    private IgniteTableWriter igniteTableWriter;

    @Mock
    private DistributedInMemoryRepository distributedInMemoryRepository;

    @Before
    public void setUp() throws Exception {
        igniteTableWriter = new IgniteTableWriter(distributedInMemoryRepository);
    }

    /*
    drop table if exists test_table_name; create table test_table_name  (field1 id varchar primary key auto_increment, field2 int,  field3 varchar);
    */
    @Test
    public void should_return_create_table_string_when_given_a_list_of_map() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("table_name", "test_table_name");
        map.put("field1", "id varchar primary key auto_increment");
        map.put("field2", "int");
        map.put("field3", "varchar");

        val result = igniteTableWriter.buildCreateTableSqlString(Arrays.asList(map)).get();

        assertThat(result.startsWith("drop table if exists test_table_name; create table test_table_name ")).isTrue();
        val subStringArray = result.substring(result.indexOf('(') + 1, result.indexOf(')')).split(",");
        assertThat(subStringArray).containsOnly("field1 id varchar primary key auto_increment", "field2 int", "field3 varchar");
    }
}