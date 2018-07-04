package com.bnpparibas.dsibddf.nomenclature.exposition.controller;

import com.bnpparibas.dsibddf.nomenclature.application.usecase.core.interactor.GetSingleItemUseCase;
import com.bnpparibas.dsibddf.nomenclature.application.usecase.core.interactor.GetSortPagingItemListUseCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//http://www.baeldung.com/spring-boot-testing

@RunWith(SpringRunner.class)
@WebMvcTest(NomenclaturesController.class)
@AutoConfigureMybatis
public class NomenclaturesControllerTest {

    @MockBean GetSortPagingItemListUseCase getSortPagingItemListUseCase;

    @MockBean GetSingleItemUseCase getSingleItemUseCase;

    @Autowired private MockMvc mockMvc;

    @Test
    public void should_return_status_ok() throws Exception {
        given(getSortPagingItemListUseCase.execute(any())).willReturn(GetSortPagingItemListUseCase.RawResponse.builder().statusCode(200).build());
        mockMvc.perform(get("/api/v1/nomenclature/pays"))
            .andExpect(status().isOk());
    }
}