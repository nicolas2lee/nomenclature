package tao.features.core.service.impl;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import tao.features.format.adapter.ContentTypeFactory;
import tao.features.format.adapter.csv.CsvProducer;
import tao.features.format.adapter.json.JsonProducer;
import tao.features.format.adapter.xml.XmlProducer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseContentTypeFactoryImplTest {

    private ResponseContentTypeFactoryImpl responseContentProducer;

    @Before
    public void setUp() throws Exception {
        responseContentProducer = new ResponseContentTypeFactoryImpl();
    }

    @Test
    public void should_return_string_stream_when_given_string_with_comma() {
        List<String> result = responseContentProducer.createStreamViaSplitComma("a,b").collect(Collectors.toList());

        assertThat(result).hasSize(2);
    }

    @Test
    public void should_return_one_string_stream_when_given_string_without_comma() {
        List<String> result = responseContentProducer.createStreamViaSplitComma("axb").collect(Collectors.toList());

        assertThat(result).hasSize(1);
    }

    @Test
    @Ignore
    // TODO: 27/06/2018 should check if we have this situation
    public void should_return_string_stream_when_given_string_empty() {
        List<String> result = responseContentProducer.createStreamViaSplitComma(null).collect(Collectors.toList());

        assertThat(result).hasSize(2);
    }

    @Test
    public void should_return_xml_producer_when_headers_contain_application_xml() {
        List<String> list = Arrays.asList("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        ContentTypeFactory contentTypeFactory = responseContentProducer.create(list);

        assertThat(contentTypeFactory).isInstanceOf(XmlProducer.class);
    }

    @Test
    public void should_return_csv_producer_when_headers_contain_text_csv() {
        List<String> list = Arrays.asList("text/html,application/xhtml+xml,text/csv;q=0.9,image/webp,image/apng,*/*;q=0.8");
        ContentTypeFactory contentTypeFactory = responseContentProducer.create(list);

        assertThat(contentTypeFactory).isInstanceOf(CsvProducer.class);
    }

    @Test
    public void should_return_csv_producer_when_headers_contain_text_plain() {
        List<String> list = Arrays.asList("text/html,application/xhtml+xml,text/plain;q=0.9,image/webp,image/apng,*/*;q=0.8");
        ContentTypeFactory contentTypeFactory = responseContentProducer.create(list);

        assertThat(contentTypeFactory).isInstanceOf(CsvProducer.class);
    }

    @Test
    public void should_return_json_producer_when_no_header_found() {
        List<String> list = Arrays.asList("text/html,application/xhtml+xml,text/what;q=0.9,image/webp,image/apng,*/*;q=0.8");
        ContentTypeFactory contentTypeFactory = responseContentProducer.create(list);

        assertThat(contentTypeFactory).isInstanceOf(JsonProducer.class);
    }
}