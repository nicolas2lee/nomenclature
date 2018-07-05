package com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.sql.exception;

import com.bnpparibas.dsibddf.nomenclature.infrastructure.exception.AbstractApplicationRuntimeException;

public class SqlWhereClauseFormatInvalidException extends AbstractApplicationRuntimeException {
    public SqlWhereClauseFormatInvalidException(String code, String message) {
        super(code, message);
    }
}
