package com.bnpparibas.dsibddf.nomenclature.application.usecase.core;

import com.bnpparibas.dsibddf.nomenclature.application.usecase.core.interactor.GetSortPagingItemListUseCase;
import cucumber.api.DataTable;
import cucumber.api.java8.En;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(value = "classpath:cucumber.xml")
public class Stepdefs implements En {

    @Autowired
    private GetSortPagingItemListUseCase getSortPagingItemListUseCase;

    private String nomenclatureName;
    private GetSortPagingItemListUseCase.Params params;
    private GetSortPagingItemListUseCase.RawResponse rawResponse;

    public Stepdefs() {
        Given("^nomenclature name \"([^\"]*)\"$", (String nomenclatureName) -> {
            this.nomenclatureName = nomenclatureName;
        });

        Given("^optional custom parameters:$", (DataTable input) -> {
            Map<String, String> map = input.asMaps(String.class, String.class).get(0);
            params = buildGetSortPagingItemListUseCaseParams(map);
            System.out.println(params);
        });

        When("^get all items$", () -> {
            try {
                rawResponse = getSortPagingItemListUseCase.execute(params);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        Then("^return all items$", () -> {
            assertThat(rawResponse.getStatusCode()).isEqualTo(200);
        });
    }

    private GetSortPagingItemListUseCase.Params buildGetSortPagingItemListUseCaseParams(Map<String, String> map) {
        return GetSortPagingItemListUseCase.Params.builder()
                .nomenclatureName(nomenclatureName)
                .selectedFields(Optional.ofNullable(map.get("selectedFields")))
                .sortField(Optional.ofNullable(map.get("sortField")))
                .sortDirection(Optional.ofNullable(map.get("sortDirection")))
                .pagingPacket(Optional.ofNullable(map.get("pagingPacket")))
                .offset(Optional.ofNullable(map.get("offset")))
                .build();
    }
}
