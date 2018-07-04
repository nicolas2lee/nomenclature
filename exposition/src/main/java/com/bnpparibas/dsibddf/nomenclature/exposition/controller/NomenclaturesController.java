package com.bnpparibas.dsibddf.nomenclature.exposition.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bnpparibas.dsibddf.nomenclature.application.usecase.core.interactor.GetSingleItemUseCase;
import com.bnpparibas.dsibddf.nomenclature.application.usecase.core.interactor.GetSortPagingItemListUseCase;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/nomenclature")
public class NomenclaturesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(NomenclaturesController.class);

    private final GetSortPagingItemListUseCase getSortPagingItemListUseCaseUseCase;
    private final GetSingleItemUseCase getSingleItemUseCase;

    NomenclaturesController(GetSortPagingItemListUseCase getSortPagingItemListUseCaseUseCase,
                            GetSingleItemUseCase getSingleItemUseCase) {
        this.getSortPagingItemListUseCaseUseCase = getSortPagingItemListUseCaseUseCase;
        this.getSingleItemUseCase = getSingleItemUseCase;
    }

    // TODO: 28/06/2018 should use cache & in which scenario
    @GetMapping(value = "/{nomenclatureName}")
    @ResponseBody
    ResponseEntity<String> list (
            @PathVariable(value="nomenclatureName") String nomenclatureName,
            @RequestParam(value="selectedFields", required = false) String selectedFields,
            @RequestParam(value="sortField", required = false) String sortField,
            @RequestParam(value="sortDirection", required = false) String sortDirection,
            @RequestParam(value="pagingPacket", required = false) String pagingPacket,
            @RequestParam(value="offset", required = false) String offset,
            HttpServletRequest request) {
        LOGGER.info(String.format("%s %s/%s", request.getMethod(), request.getRequestURL(), nomenclatureName));
        final String header = request.getHeader("accept");
        LOGGER.info(String.format("HTTP header: accept : %s", header));
        final GetSortPagingItemListUseCase.Params userRequest = buildGetSortPagingNomenclatureListParams(nomenclatureName, selectedFields,
                sortField, sortDirection, pagingPacket, offset, header);
        GetSortPagingItemListUseCase.RawResponse rawResponse = null;
        try {
            rawResponse = getSortPagingItemListUseCaseUseCase.execute(userRequest);
            LOGGER.debug(rawResponse.toString());
            return ResponseEntity.status(rawResponse.getStatusCode()).contentType(MediaType.valueOf(rawResponse.getHeader())).body(rawResponse.getBodyString());
        } catch (SQLException e) {
            LOGGER.error("Sql exception :",e);
            return ResponseEntity.status(rawResponse.getStatusCode()).contentType(MediaType.valueOf(rawResponse.getHeader())).body(rawResponse.getBodyString());
        }
    }


    private GetSortPagingItemListUseCase.Params buildGetSortPagingNomenclatureListParams(String nomenclatureName, String selectedFields,
                                                                                         String sortField, String sortDirection, String pagingPacket, String offset,
                                                                                         String header) {

        return GetSortPagingItemListUseCase.Params.builder()
                .selectedFields(Optional.ofNullable(selectedFields))
                .sortField(Optional.ofNullable(sortField)).sortDirection(Optional.ofNullable(sortDirection))
                .pagingPacket(Optional.ofNullable(pagingPacket)).offset(Optional.ofNullable(offset))
                .nomenclatureName(nomenclatureName)
                .header(Arrays.asList(header))
                .build();
    }

    @GetMapping(value = "/{nomenclatureName}/{id}")
    ResponseEntity item (
            @PathVariable(value="nomenclatureName") String nomenclatureName,
            @PathVariable(value="id") String id,
            @RequestParam(value="selectedFields", required = false) String selectedFields,
            HttpServletRequest request){
        LOGGER.info(String.format("%s %s/%s/%s", request.getMethod(), request.getPathInfo(), nomenclatureName, id));
        final String header = request.getHeader("accept");
        LOGGER.info(String.format("HTTP header: accept : %s", header));
        final GetSingleItemUseCase.Params params = buildGetSingleNomenclatureParams(nomenclatureName, id, selectedFields, header);
        GetSingleItemUseCase.RawResponse rawResponse = getSingleItemUseCase.execute(params);
        return ResponseEntity.status(rawResponse.getStatusCode()).contentType(MediaType.valueOf(rawResponse.getHeader())).body(rawResponse.getBodyString());
    }


    private GetSingleItemUseCase.Params buildGetSingleNomenclatureParams(String nomenclatureName, String id, String selectedFields, String header) {
        return GetSingleItemUseCase.Params.builder()
                .nomenclatureName(nomenclatureName)
                .id(id)
                .selectedFields(Optional.ofNullable(selectedFields))
                .header(Arrays.asList(header))
                .build();
    }
}