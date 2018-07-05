package com.bnpparibas.dsibddf.nomenclature.exposition.controller;

import com.bnpparibas.dsibddf.nomenclature.application.usecase.core.interactor.GetSortPagingItemListUseCase;
import com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.sql.exception.SqlWhereClauseFormatInvalidException;
import com.bnpparibas.dsibddf.nomenclature.infrastructure.resource.exception.ResourceNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({NomenclaturesController.class, CustomRestExceptionHandler.class})
@AutoConfigureMybatis
public class CustomRestExceptionHandlerTest {
    @MockBean GetSortPagingItemListUseCase getSortPagingItemListUseCase;

    @Autowired private MockMvc mockMvc;

    @Test
    public void should_return_internal_server_error_when_catch_ResourceNotFoundException_for_GetSortPagingItemListUseCase() throws Exception {
        given(getSortPagingItemListUseCase.execute(any())).willThrow(new ResourceNotFoundException("test code","test only"));
        mockMvc.perform(get("/api/v1/nomenclatures/pays"))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    public void should_return_internal_server_error_when_catch_SqlWhereClauseFormatInvalidException_exception_for_GetSortPagingItemListUseCase() throws Exception {
        given(getSortPagingItemListUseCase.execute(any())).willThrow(new SqlWhereClauseFormatInvalidException("test code","test only"));
        mockMvc.perform(get("/api/v1/nomenclatures/pays"))
                .andDo(print())
                .andExpect(status().isInternalServerError())
        ;
    }
}