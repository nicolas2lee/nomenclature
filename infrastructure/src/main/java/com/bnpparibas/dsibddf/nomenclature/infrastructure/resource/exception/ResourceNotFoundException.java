package com.bnpparibas.dsibddf.nomenclature.infrastructure.resource.exception;

public class ResourceNotFoundException extends RuntimeException {
    private String code;

    public ResourceNotFoundException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
