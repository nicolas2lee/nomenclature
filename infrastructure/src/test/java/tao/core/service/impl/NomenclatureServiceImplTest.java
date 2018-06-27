package tao.core.service.impl;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import tao.core.mapper.ItemMapper;
import tao.core.model.Clause;
import tao.core.model.Nomenclature;
import tao.core.model.Paging;
import tao.core.service.exception.SqlWhereClauseFormatInvalidException;
import tao.resource.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class NomenclatureServiceImplTest {

    private NomenclatureServiceImpl nomenclatureService;

    @Mock
    ItemMapper itemMapper;

    @Before
    public void setUp() throws Exception {
        nomenclatureService = new NomenclatureServiceImpl(itemMapper);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void should_throw_ResouceNotFoundException_when_nomenclature_name_is_null() {
        nomenclatureService.getDefaultNomenclatureConfig(null);
    }


    @Test(expected = ResourceNotFoundException.class)
    public void should_throw_ResouceNotFoundException_when_yaml_file_with_nomenclature_name_is_not_found() {
        nomenclatureService.getDefaultNomenclatureConfig("hello");
    }

    @Test
    public void should_return_optional_empty_when_nomenclature_is_not_enabled() {
        Optional<Nomenclature> result = nomenclatureService.getDefaultNomenclatureConfig("pays_not_enabled");

        assertThat(result).isEqualTo(Optional.empty());
    }

    @Test
    public void should_return_nomencalture_when_nomenclature_is_enabled() {
        Optional<Nomenclature> result = nomenclatureService.getDefaultNomenclatureConfig("pays_enabled");

        assertThat(result).isNotEqualTo(Optional.empty());
        assertThat(result.get().isEnabled()).isTrue();
    }

    @Test
    public void should_return_sql_string_values_when_given_not_empty_list() {
        String[] stringArray = {"a", "b", "c"};
        final String result = nomenclatureService.buildSqlStringValues(stringArray);

        assertThat(result).isEqualTo("'a', 'b', 'c'");
    }

    @Test
    public void should_return_empty_sql_string_when_given_empty_list() {
        String[] stringArray = {};
        final String result = nomenclatureService.buildSqlStringValues(stringArray);

        assertThat(result).isEqualTo("''");
    }

    @Test
    public void should_return_sql_string_when_given_list_with_one_element() {
        String[] stringArray = {"a"};
        final String result = nomenclatureService.buildSqlStringValues(stringArray);

        assertThat(result).isEqualTo("'a'");
    }

    @Test
    public void should_return_valid_sql_in_statement_when_clauses_is_not_empty() {
        String[] strings = {"a", "b", "c"};
        final Clause clause1 = Clause.builder().name("abc").values(strings).build();
        final Clause clause2 = Clause.builder().name("edf").values(strings).build();

        final List<String> result = nomenclatureService.buildVaraibleInValuesStatement(Arrays.asList(clause1, clause2));

        assertThat(result.get(0)).isEqualTo("abc IN ( 'a', 'b', 'c' )");
        assertThat(result.get(1)).isEqualTo("edf IN ( 'a', 'b', 'c' )");
    }

    @Test(expected = SqlWhereClauseFormatInvalidException.class)
    public void should_return_SqlWhereClauseFormatInvalidException_when_clauses_is_empty() {
        nomenclatureService.buildVaraibleInValuesStatement(new ArrayList<>());
    }

    @Test
    public void should_return_1_equals_1_clause_when_clause_is_empty() {
        String result = nomenclatureService.buildWhereClause(new ArrayList<>());

        assertThat(result).isEqualTo("1=1");
    }

    @Test
    public void should_return_correct_limit_clause_when_given_offset_paging_number() {
        final Paging paging = Paging.builder().enabled(true).build();

        final String result = nomenclatureService.buildLimitClause(paging, "3", "10");

        assertThat(result).isEqualTo("limit 3, 10");
    }
}