package com.bnpparibas.dsibddf.nomenclature.exposition.controller;

import com.bnpparibas.dsibddf.nomenclature.exposition.model.ExceptionResponse;
import com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.sql.exception.SqlWhereClauseFormatInvalidException;
import com.bnpparibas.dsibddf.nomenclature.infrastructure.resource.exception.ResourceNotFoundException;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFound(ResourceNotFoundException ex) {
        val exceptionResponse = ExceptionResponse.builder()
                .code(String.format("%s.%s", HttpStatus.NOT_FOUND.name(), ex.getCode()))
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(SqlWhereClauseFormatInvalidException.class)
    public ResponseEntity<ExceptionResponse> sqlWhereClauseFormatInvalid(SqlWhereClauseFormatInvalidException ex) {
        val exceptionResponse = ExceptionResponse.builder()
                .code(String.format("%s.%s", HttpStatus.INTERNAL_SERVER_ERROR.name(), ex.getCode()))
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }
}
