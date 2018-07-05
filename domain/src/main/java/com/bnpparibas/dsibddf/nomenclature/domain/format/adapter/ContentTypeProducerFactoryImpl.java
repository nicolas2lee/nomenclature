package com.bnpparibas.dsibddf.nomenclature.domain.format.adapter;


import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Named
class ContentTypeProducerFactoryImpl implements ContentTypeProducerFactory {

    @Inject
    ContentTypeProducerFactoryImpl() {
    }

    @Override
    public ContentTypeProducer create(List<String> headers) {
        List<String> contentTypes = headers.stream().flatMap(this::createStreamViaSplitComma).map(String::toLowerCase).collect(Collectors.toList());
        if (contentTypes.contains(MediaType.APPLICATION_XML_VALUE.getValue()))
            return ContentTypeProducer.create(MediaType.APPLICATION_XML_VALUE);
        if (contentTypes.contains(MediaType.CSV_VALUE.getValue()))
            return ContentTypeProducer.create(MediaType.CSV_VALUE);
        if (contentTypes.contains(MediaType.TEXT_PLAIN_VALUE.getValue()))
            return ContentTypeProducer.create(MediaType.TEXT_PLAIN_VALUE);
        return ContentTypeProducer.create(MediaType.APPLICATION_JSON_VALUE);
    }

    Stream<String> createStreamViaSplitComma(String header) {
        return Arrays.stream(header.split(";")).flatMap(sub -> Arrays.stream(sub.split(",")));
    }
}
