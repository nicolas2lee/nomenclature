package com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.sql.exception;

public class SqlWhereClauseFormatInvalidException extends RuntimeException {
    public SqlWhereClauseFormatInvalidException(String message) {
        super(message);
    }
}
