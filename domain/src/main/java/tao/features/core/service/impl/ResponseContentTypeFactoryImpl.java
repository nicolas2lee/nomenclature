package tao.features.core.service.impl;


import tao.features.core.ResponseContentTypeFactory;
import tao.features.format.adapter.ContentTypeFactory;
import tao.features.format.adapter.MediaType;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Named
class ResponseContentTypeFactoryImpl implements ResponseContentTypeFactory {

    @Inject
    ResponseContentTypeFactoryImpl() {

    }

    @Override
    public ContentTypeFactory create(List<String> headers) {
        List<String> contentTypes = headers.stream().flatMap(header -> createStreamViaSplitComma(header)).map(s -> s.toLowerCase()).collect(Collectors.toList());
        if (contentTypes.contains(MediaType.APPLICATION_XML_VALUE.getValue()))
            return ContentTypeFactory.create(MediaType.APPLICATION_XML_VALUE);
        if (contentTypes.contains(MediaType.CSV_VALUE.getValue()))
            return ContentTypeFactory.create(MediaType.CSV_VALUE);
        if (contentTypes.contains(MediaType.TEXT_PLAIN_VALUE.getValue()))
            return ContentTypeFactory.create(MediaType.TEXT_PLAIN_VALUE);
        return ContentTypeFactory.create(MediaType.APPLICATION_JSON);
    }

    Stream<String> createStreamViaSplitComma(String header) {
        return Arrays.asList(header.split(";")).stream().flatMap(sub -> Arrays.stream(sub.split(",")));
    }
}
