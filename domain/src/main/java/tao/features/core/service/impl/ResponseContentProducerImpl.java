package tao.features.core.service.impl;


import lombok.Getter;
import tao.features.core.ResponseContentProducer;
import tao.features.format.adapter.ContentTypeFactory;
import tao.features.format.adapter.MediaType;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Named
class ResponseContentProducerImpl implements ResponseContentProducer {

    @Getter
    private ContentTypeFactory contentTypeFactory;

    @Inject
    ResponseContentProducerImpl() {

    }

    @Override
    public void create(List<String> headers) {
        List<String> contentTypes = headers.stream().flatMap(header -> createStreamViaSplitComma(header)).map(s -> s.toLowerCase()).collect(Collectors.toList());
        if (contentTypes.contains(MediaType.APPLICATION_XML_VALUE.getValue())) {
            this.contentTypeFactory = ContentTypeFactory.create(MediaType.APPLICATION_XML_VALUE);
            return;
        }
        if (contentTypes.contains(MediaType.CSV_VALUE.getValue())) {
            this.contentTypeFactory = ContentTypeFactory.create(MediaType.CSV_VALUE);
            return;
        }
        if (contentTypes.contains(MediaType.TEXT_PLAIN_VALUE.getValue())) {
            this.contentTypeFactory = ContentTypeFactory.create(MediaType.TEXT_PLAIN_VALUE);
            return;
        }
        this.contentTypeFactory = ContentTypeFactory.create(MediaType.APPLICATION_JSON);
    }

    // TODO: 27/06/2018 need to add test
    @Override
    public String produce(Map<String, Object> items) {
        return contentTypeFactory.produce(items);
    }

    Stream<String> createStreamViaSplitComma(String header) {
        return Arrays.asList(header.split(";")).stream().flatMap(sub -> Arrays.stream(sub.split(",")));
    }
}
