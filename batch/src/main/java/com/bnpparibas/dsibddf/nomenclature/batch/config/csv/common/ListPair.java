package com.bnpparibas.dsibddf.nomenclature.batch.config.csv.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;

import java.util.Collection;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class ListPair {
    private Set<String> keySet;
    private Collection<String> valueList;

    public String keySetToSqlExpression() {
        return buildSqlKeyExpression(keySet);
    }

    public String valueListToSqlExpression() {
        return buildSqlValueExpression(valueList);
    }

    private String buildSqlKeyExpression(Collection<String> collection) {
        return String.join(",", collection);
    }

    private String buildSqlValueExpression(Collection<String> collection) {
        val list = collection.stream().map(buildSingleSqlString())
                .collect(Collectors.toList());
        return String.join(",", list);
    }

    private UnaryOperator<String> buildSingleSqlString() {
        return e -> String.format("'%s'", e);
    }
}
