package tao.usecase.nomenclature.service.impl;


import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;


public class QueryParametersFactoryImplTest {

    private QueryParametersFactoryImpl queryParametersFactory;

    @Before
    public void setUp() throws Exception {
        queryParametersFactory = new QueryParametersFactoryImpl();
    }

    @Test
    public void should_return_empty_list_when_sortField_is_not_present_in_request() {
        final List<String> result = queryParametersFactory.extractSelectedFields(Optional.empty());

        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void should_return_string_list_when_sortField_is_present_in_request() {
        final List<String> result = queryParametersFactory.extractSelectedFields(Optional.of("a,b,c"));

        assertThat(result).hasSize(3);
    }

    @Test
    public void should_return_string_list_with_one_element_when_sortField_is_present_and_only_one_field_in_request() {
        final List<String> result = queryParametersFactory.extractSelectedFields(Optional.of("a"));

        assertThat(result).hasSize(1);
    }

    @Test
    public void should_return_default_map_when_selected_field_is_empty() {
        final Map<String, String> defaultMap = new HashMap<>();
        final List<String> selectedFields = Collections.emptyList();

        final Map<String, String> result = queryParametersFactory.fixSelectedFields(selectedFields, defaultMap);

        assertThat(result).isEqualTo(defaultMap);
    }

    @Test
    public void should_return_empty_map_when_default_map_not_contain_selected_field() {
        Map<String, String> defaultMap = new HashMap<>();
        defaultMap.put("a", "a");
        final List<String> selectedFields = Arrays.asList("b");

        final Map<String, String> result = queryParametersFactory.fixSelectedFields(selectedFields, defaultMap);

        assertThat(result).isEmpty();
    }

    @Test
    public void should_return_map_filtering_not_in_default_map() {
        Map<String, String> defaultMap = new HashMap<>();
        defaultMap.put("a", "a");
        defaultMap.put("b", "b");
        final List<String> selectedFields = Arrays.asList("a", "b", "c");

        final Map<String, String> result = queryParametersFactory.fixSelectedFields(selectedFields, defaultMap);

        assertThat(result).hasSize(2);
        assertThat(result.get("a")).isEqualTo("a");
        assertThat(result.get("b")).isEqualTo("b");
    }

    @Test
    public void should_return_first_default_sort_field_when_use_request_sort_field_is_not_present() {
        final Optional<String> sortField = Optional.empty();
        final List<String> defaultSortFields = Arrays.asList("a", "b");

        final String result = queryParametersFactory.fixSort(sortField, defaultSortFields);

        assertThat(result).isEqualTo("a");
    }

    @Test
    public void should_return_first_default_sort_field_when_use_request_sort_field_is_empty() {
        final Optional<String> sortField = Optional.of("");
        final List<String> defaultSortFields = Arrays.asList("a", "b");

        final String result = queryParametersFactory.fixSort(sortField, defaultSortFields);

        assertThat(result).isEqualTo("a");
    }

    @Test
    public void should_return_first_default_sort_field_when_default_sort_fields_do_not_containe_user_request_field() {
        final Optional<String> sortField = Optional.of("a");
        final List<String> defaultSortFields = Arrays.asList("b", "c");

        final String result = queryParametersFactory.fixSort(sortField, defaultSortFields);

        assertThat(result).isEqualTo("b");
    }

    @Test
    public void should_return_user_request_sort_field_when_default_sort_fields_not_containe_user_request_field() {
        final Optional<String> sortField = Optional.of("a");
        final List<String> defaultSortFields = Arrays.asList("a", "c");

        final String result = queryParametersFactory.fixSort(sortField, defaultSortFields);

        assertThat(result).isEqualTo("a");
    }

    @Test
    public void should_return_default_value_when_paging_number_is_not_present() {
        final Optional<String> pagingNumber = Optional.empty();

        final String result = queryParametersFactory.fixPagingNumber(pagingNumber, "100");

        assertThat(result).isEqualTo("100");
    }

    @Test
    public void should_return_default_value_when_paging_number_is_negative() {
        final Optional<String> pagingNumber = Optional.of("-1");

        final String result = queryParametersFactory.fixPagingNumber(pagingNumber, "100");

        assertThat(result).isEqualTo("100");
    }

    @Test
    public void should_return_default_value_when_paging_number_is_zero() {
        final Optional<String> pagingNumber = Optional.of("0");

        final String result = queryParametersFactory.fixPagingNumber(pagingNumber, "100");

        assertThat(result).isEqualTo("100");
    }

    @Test
    public void should_return_user_request_value_when_paging_number_is_positive() {
        final Optional<String> pagingNumber = Optional.of("20");

        final String result = queryParametersFactory.fixPagingNumber(pagingNumber, "100");

        assertThat(result).isEqualTo("20");
    }

    @Test
    public void should_return_default_value_when_fail_to_convert_user_request_string_to_number() {
        final Optional<String> pagingNumber = Optional.of("20AH");

        final String result = queryParametersFactory.fixPagingNumber(pagingNumber, "100");

        assertThat(result).isEqualTo("100");
    }
}