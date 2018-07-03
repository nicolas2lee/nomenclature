package tao.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tao.usecase.nomenclature.interactor.GetSingleNomenclatureUseCase;
import tao.usecase.nomenclature.interactor.GetSortPagingNomenclatureListUseCase;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/nomenclature")
public class NomenclaturesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(NomenclaturesController.class);

    private static final String SELECTED_FIELDS_QUERY_PARAMETER = "selectedFields";
    private static final String SORT_FIELD_QUERY_PARAMETER = "sortField";
    private static final String SORT_DIRECTION_QUERY_PARAMETER = "sortDirection";
    private static final String PAGING_PACKET_QUERY_PARAMETER = "pagingPacket";
    private static final String OFFSET_QUERY_PARAMETER = "offset";
    private static final String ID_QUERY_PARAMETER = "id";


    private final GetSortPagingNomenclatureListUseCase getSortPagingNomenclatureListUseCaseUseCase;
    private final GetSingleNomenclatureUseCase getSingleNomenclatureUseCase;

    NomenclaturesController(GetSortPagingNomenclatureListUseCase getSortPagingNomenclatureListUseCaseUseCase,
                        GetSingleNomenclatureUseCase getSingleNomenclatureUseCase) {
        this.getSortPagingNomenclatureListUseCaseUseCase = getSortPagingNomenclatureListUseCaseUseCase;
        this.getSingleNomenclatureUseCase = getSingleNomenclatureUseCase;
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
        final GetSortPagingNomenclatureListUseCase.Params userRequest = buildGetSortPagingNomenclatureListParams(nomenclatureName, selectedFields,
                sortField, sortDirection, pagingPacket, offset, header);
        GetSortPagingNomenclatureListUseCase.RawResponse rawResponse = getSortPagingNomenclatureListUseCaseUseCase.execute(userRequest);
        LOGGER.info(rawResponse.toString());
        return ResponseEntity.status(rawResponse.getStatusCode()).contentType(MediaType.valueOf(rawResponse.getHeader())).body(rawResponse.getBodyString());
    }


    private GetSortPagingNomenclatureListUseCase.Params buildGetSortPagingNomenclatureListParams(String nomenclatureName, String selectedFields,
                                                                                                 String sortField, String sortDirection, String pagingPacket, String offset,
                                                                                                 String header) {

        return GetSortPagingNomenclatureListUseCase.Params.builder()
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
        final GetSingleNomenclatureUseCase.Params params = buildGetSingleNomenclatureParams(nomenclatureName, id, selectedFields, header);
        GetSingleNomenclatureUseCase.RawResponse rawResponse = getSingleNomenclatureUseCase.execute(params);
        return ResponseEntity.status(rawResponse.getStatusCode()).contentType(MediaType.valueOf(rawResponse.getHeader())).body(rawResponse.getBodyString());
    }


    private GetSingleNomenclatureUseCase.Params buildGetSingleNomenclatureParams(String nomenclatureName, String id, String selectedFields, String header) {
        return GetSingleNomenclatureUseCase.Params.builder()
                .nomenclatureName(nomenclatureName)
                .id(id)
                .selectedFields(Optional.ofNullable(selectedFields))
                .header(Arrays.asList(header))
                .build();
    }
}