package com.bnpparibas.dsibddf.nomenclature.domain.format.adapter.csv;

import com.bnpparibas.dsibddf.nomenclature.domain.format.adapter.ContentTypeProducer;
import com.bnpparibas.dsibddf.nomenclature.domain.format.adapter.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CsvProducer extends ContentTypeProducer {
    @Override
    public String produce(Map<String, Object> items) {
        super.setHttpContentTypeHeader(MediaType.TEXT_PLAIN_VALUE.getValue());
        String type;
        List<String> r;
        List<String> finalString = new ArrayList<>();
        List<Map> elements;
        for (String key : items.keySet()) {
            type = items.get(key).getClass().getSimpleName();
            if (type.equals("Integer")) {
                //Le sommaire
                finalString.add('"' + items.get(key).toString().replace('"', '\"') + '"' + "\n");

            } else {
                //Les éléments
                elements = (ArrayList) items.get(key);
                for (Map element : elements) {
                    r = new ArrayList<>();
                    for (Object k : element.keySet()) {
                        r.add('"' + element.get(k).toString().replace('"', '\"') + '"');
                    }
                    finalString.add(String.join(";", r));
                    finalString.add("\n");
                }
            }
        }

        return String.join("", finalString);
    }
}
