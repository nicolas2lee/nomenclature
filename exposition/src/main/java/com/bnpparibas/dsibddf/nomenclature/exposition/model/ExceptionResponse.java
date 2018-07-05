package com.bnpparibas.dsibddf.nomenclature.exposition.model;

import lombok.Builder;
import lombok.Value;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Value
public class ExceptionResponse {

    private Timestamp timestamp;
    private UUID uuid;
    private String code;
    private String message;

    @Builder
    public ExceptionResponse(String code, String message){
        this.code = code;
        this.message = message;
        this.uuid = UUID.randomUUID();
        this.timestamp = Timestamp.from(Instant.now());
    }


}
