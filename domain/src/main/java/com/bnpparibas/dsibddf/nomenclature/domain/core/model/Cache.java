package com.bnpparibas.dsibddf.nomenclature.domain.core.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Cache {
    private Boolean enabled;
    private String expiration;
}
