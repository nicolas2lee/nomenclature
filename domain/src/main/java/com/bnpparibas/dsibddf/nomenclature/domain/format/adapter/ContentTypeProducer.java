package com.bnpparibas.dsibddf.nomenclature.domain.format.adapter;

import com.bnpparibas.dsibddf.nomenclature.domain.format.adapter.csv.CsvProducer;
import com.bnpparibas.dsibddf.nomenclature.domain.format.adapter.json.JsonProducer;

import java.util.Map;


public abstract class ContentTypeProducer {
    private String httpContentTypeHeader = "Not defined";

    public static ContentTypeProducer create(MediaType format) {
        switch (format) {
            // TODO: 04/07/2018 no need to support xml
            //case APPLICATION_XML_VALUE:
            //    return new XmlProducer();
            case TEXT_PLAIN_VALUE:
            case CSV_VALUE:
                return new CsvProducer();
            default:
                return new JsonProducer();
        }
    }

    public String getHttpContentTypeHeader() {
        return httpContentTypeHeader;
    }

    public void setHttpContentTypeHeader(String httpContentTypeHeader) {
        this.httpContentTypeHeader = httpContentTypeHeader;
    }

    public abstract String produce(Map<String, Object> items);
}
