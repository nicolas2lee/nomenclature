package tao.handler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import tao.core.NomenclatureService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class NomenclatureHandlerTest {

    private NomenclatureHandler nomenclatureHandler;

    @Mock
    private NomenclatureService nomenclatureService;

    @Before
    public void setUp() throws Exception {
        nomenclatureHandler = new NomenclatureHandler(nomenclatureService);
    }

    @Test
    public void should_return_empty_list_when_sortField_is_not_present_in_request() {
        List<String> result = nomenclatureHandler.getQueriedSelectedFields(Optional.empty());

        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void should_return_string_list_when_sortField_is_present_in_request() {
        List<String> result = nomenclatureHandler.getQueriedSelectedFields(Optional.of("a,b,c"));

        assertThat(result).hasSize(3);
    }

    @Test
    public void should_return_string_list_with_one_element_when_sortField_is_present_and_only_one_field_in_request() {
        List<String> result = nomenclatureHandler.getQueriedSelectedFields(Optional.of("a"));

        assertThat(result).hasSize(1);
    }
}