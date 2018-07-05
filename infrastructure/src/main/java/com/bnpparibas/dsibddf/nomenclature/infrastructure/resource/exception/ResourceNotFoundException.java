package com.bnpparibas.dsibddf.nomenclature.infrastructure.resource.exception;

import com.bnpparibas.dsibddf.nomenclature.infrastructure.exception.AbstractApplicationRuntimeException;

public class ResourceNotFoundException extends AbstractApplicationRuntimeException {
    public ResourceNotFoundException(String code, String message) {
        super(code, message);
    }
}
