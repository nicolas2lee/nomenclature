package tao.usecase.nomenclature.core.repository.sql.impl;

import org.junit.Before;
import org.junit.Test;
import tao.usecase.nomenclature.core.model.Clause;
import tao.usecase.nomenclature.core.model.Paging;
import tao.usecase.nomenclature.core.repository.sql.exception.SqlWhereClauseFormatInvalidException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SqlHelperImplTest {

    private SqlHelperImpl sqlHelper;

    @Before
    public void setUp() throws Exception {
        sqlHelper = new SqlHelperImpl();
    }

    @Test
    public void should_return_sql_string_values_when_given_not_empty_list() {
        List<String> strings = Arrays.asList("a", "b", "c");
        final String result = sqlHelper.buildSqlStringValues(strings);

        assertThat(result).isEqualTo("'a', 'b', 'c'");
    }

    @Test
    public void should_return_empty_sql_string_when_given_empty_list() {
        List<String> strings = Arrays.asList();
        final String result = sqlHelper.buildSqlStringValues(strings);

        assertThat(result).isEqualTo("''");
    }

    @Test
    public void should_return_sql_string_when_given_list_with_one_element() {
        List<String> strings = Arrays.asList("a");
        final String result = sqlHelper.buildSqlStringValues(strings);

        assertThat(result).isEqualTo("'a'");
    }

    @Test
    public void should_return_valid_sql_in_statement_when_clauses_is_not_empty() {
        List<String> strings = Arrays.asList("a", "b", "c");
        final Clause clause1 = Clause.builder().name("abc").values(strings).build();
        final Clause clause2 = Clause.builder().name("edf").values(strings).build();

        final List<String> result = sqlHelper.buildVaraibleInValuesStatement(Arrays.asList(clause1, clause2));

        assertThat(result.get(0)).isEqualTo("abc IN ( 'a', 'b', 'c' )");
        assertThat(result.get(1)).isEqualTo("edf IN ( 'a', 'b', 'c' )");
    }

    @Test(expected = SqlWhereClauseFormatInvalidException.class)
    public void should_return_SqlWhereClauseFormatInvalidException_when_clauses_is_empty() {
        sqlHelper.buildVaraibleInValuesStatement(new ArrayList<>());
    }

    @Test
    public void should_return_1_equals_1_clause_when_clause_is_empty() {
        String result = sqlHelper.buildWhereClause(new ArrayList<>());

        assertThat(result).isEqualTo("1=1");
    }

    @Test
    public void should_return_correct_limit_clause_when_given_offset_paging_number() {
        final Paging paging = Paging.builder().enabled(true).build();

        final String result = sqlHelper.buildLimitClause(paging, "3", "10");

        assertThat(result).isEqualTo("limit 3, 10");
    }
}