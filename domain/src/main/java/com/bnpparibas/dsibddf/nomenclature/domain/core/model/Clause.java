package com.bnpparibas.dsibddf.nomenclature.domain.core.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Clause {
    private String name;
    private List<String> values;
}