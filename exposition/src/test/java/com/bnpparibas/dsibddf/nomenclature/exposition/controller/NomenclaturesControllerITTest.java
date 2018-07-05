package com.bnpparibas.dsibddf.nomenclature.exposition.controller;

import com.bnpparibas.dsibddf.nomenclature.application.usecase.core.interactor.GetSingleItemUseCase;
import com.bnpparibas.dsibddf.nomenclature.application.usecase.core.interactor.GetSortPagingItemListUseCase;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.SQLException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(NomenclaturesController.class)
@AutoConfigureMybatis
public class NomenclaturesControllerITTest {

    @MockBean GetSortPagingItemListUseCase getSortPagingItemListUseCase;

    @MockBean GetSingleItemUseCase getSingleItemUseCase;

    @Autowired private MockMvc mockMvc;

    @Test
    public void should_return_status_ok_for_GetSortPagingItemListUseCase() throws Exception {
        given(getSortPagingItemListUseCase.execute(any())).willReturn(GetSortPagingItemListUseCase.RawResponse.builder().statusCode(200).build());
        mockMvc.perform(get("/api/v1/nomenclatures/pays"))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_internal_server_error_when_catch_sql_exception_for_GetSortPagingItemListUseCase() throws Exception {
        given(getSortPagingItemListUseCase.execute(any())).willThrow(new SQLException("test only"));
        mockMvc.perform(get("/api/v1/nomenclatures/pays"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void should_return_resource_not_found_error_when_nomenclature_yaml_file_not_found_for_GetSortPagingItemListUseCase() throws Exception {
        given(getSortPagingItemListUseCase.execute(any())).willReturn(GetSortPagingItemListUseCase.RawResponse.builder().statusCode(404).build());
        mockMvc.perform(get("/api/v1/nomenclatures/what"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_return_status_ok_for_GetSingleItemUseCase() throws Exception {
        given(getSingleItemUseCase.execute(any())).willReturn(GetSingleItemUseCase.RawResponse.builder().statusCode(200).build());
        mockMvc.perform(get("/api/v1/nomenclatures/pays/france"))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_internal_server_error_when_catch_sql_exception_for_GetSingleItemUseCase() throws Exception {
        given(getSingleItemUseCase.execute(any())).willThrow(new SQLException("test only"));
        mockMvc.perform(get("/api/v1/nomenclatures/pays/france"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void should_return_resource_not_found_error_when_nomenclature_yaml_file_not_found_for_GetSingleItemUseCase() throws Exception {
        given(getSingleItemUseCase.execute(any())).willReturn(GetSingleItemUseCase.RawResponse.builder().statusCode(404).build());
        mockMvc.perform(get("/api/v1/nomenclatures/pays/france"))
                .andExpect(status().isNotFound());
    }
}