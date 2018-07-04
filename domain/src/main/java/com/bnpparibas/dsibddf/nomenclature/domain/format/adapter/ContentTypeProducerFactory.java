package com.bnpparibas.dsibddf.nomenclature.domain.format.adapter;

import java.util.List;

public interface ContentTypeProducerFactory {
    ContentTypeProducer create(List<String> headers);
}
