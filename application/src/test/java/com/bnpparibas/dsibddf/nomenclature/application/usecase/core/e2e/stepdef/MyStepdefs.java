package com.bnpparibas.dsibddf.nomenclature.application.usecase.core.e2e.stepdef;

import com.bnpparibas.dsibddf.nomenclature.application.usecase.core.interactor.GetSortPagingItemListUseCase;
import com.bnpparibas.dsibddf.nomenclature.application.usecase.core.service.QueryParametersFactory;
import com.bnpparibas.dsibddf.nomenclature.domain.core.NomenclatureConfig;
import com.bnpparibas.dsibddf.nomenclature.domain.core.model.Nomenclature;
import com.bnpparibas.dsibddf.nomenclature.domain.core.model.Summary;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


@Profile("inmemory")
@ContextConfiguration(value = "classpath:cucumber.xml")
public class MyStepdefs {

    @Autowired
    private GetSortPagingItemListUseCase getSortPagingItemListUseCase;

    @Autowired
    NomenclatureConfig nomenclatureConfig;

    @MockBean
    QueryParametersFactory queryParametersFactory;

    private String nomenclatureName;
    private String header;
    private GetSortPagingItemListUseCase.Params params;
    private GetSortPagingItemListUseCase.RawResponse rawResponse;

    @Given("^nomenclature name \"([^\"]*)\"$")
    public void test(String name){
        this.nomenclatureName = nomenclatureName;
    }

    @Given("^default header \"([^\"]*)\"$")
    public void defaultHeader(String header) throws Throwable {
        this.header = header;
    }

    @Given("^optional custom parameters:$")
    public void optionalCustomParameters(DataTable input) throws Throwable {
        Map<String, String> map = input.asMaps(String.class, String.class).get(0);
        params = buildGetSortPagingItemListUseCaseParams(map);
    }

    @When("^get all items$")
    public void getAllItems() throws Throwable {
        try {
            rawResponse = getSortPagingItemListUseCase.execute(params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Then("^return all items$")
    public void returnAllItems() throws Throwable {
        assertThat(rawResponse.getStatusCode()).isEqualTo(200);
    }


    private GetSortPagingItemListUseCase.Params buildGetSortPagingItemListUseCaseParams(Map<String, String> map) {
        return GetSortPagingItemListUseCase.Params.builder()
                .nomenclatureName(nomenclatureName)
                .header(Arrays.asList(header))
                .selectedFields(Optional.ofNullable(map.get("selectedFields")))
                .sortField(Optional.ofNullable(map.get("sortField")))
                .sortDirection(Optional.ofNullable(map.get("sortDirection")))
                .pagingPacket(Optional.ofNullable(map.get("pagingPacket")))
                .offset(Optional.ofNullable(map.get("offset")))
                .build();
    }

    @Given("^nomenclature default config$")
    public void nomenclatureDefaultConfig() throws Throwable {
        val nomenclature = Nomenclature.builder()
                .summary(Summary.builder().enabled(false).build()).build();
        given(nomenclatureConfig.getDefaultConfig(nomenclatureName)).willReturn(nomenclature);

    }
}
