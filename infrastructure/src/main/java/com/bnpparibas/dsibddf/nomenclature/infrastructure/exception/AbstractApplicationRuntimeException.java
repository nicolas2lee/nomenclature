package com.bnpparibas.dsibddf.nomenclature.infrastructure.exception;

import lombok.Getter;

public abstract class AbstractApplicationRuntimeException extends RuntimeException {
    @Getter private final String code;

    public AbstractApplicationRuntimeException(String code, String message){
        super(message);
        this.code = code;
    }
}
