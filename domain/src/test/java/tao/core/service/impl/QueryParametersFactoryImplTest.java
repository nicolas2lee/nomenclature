package tao.core.service.impl;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class QueryParametersFactoryImplTest {

    private QueryParametersFactoryImpl queryParametersFactory;

    @Before
    public void setUp() throws Exception {
        queryParametersFactory = new QueryParametersFactoryImpl();
    }

    @Test
    public void should_return_empty_list_when_sortField_is_not_present_in_request() {
        List<String> result = queryParametersFactory.extractSelectedFields(Optional.empty());

        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void should_return_string_list_when_sortField_is_present_in_request() {
        List<String> result = queryParametersFactory.extractSelectedFields(Optional.of("a,b,c"));

        assertThat(result).hasSize(3);
    }

    @Test
    public void should_return_string_list_with_one_element_when_sortField_is_present_and_only_one_field_in_request() {
        List<String> result = queryParametersFactory.extractSelectedFields(Optional.of("a"));

        assertThat(result).hasSize(1);
    }
}