package tao.features.format.adapter;

import tao.features.format.adapter.csv.CsvProducer;
import tao.features.format.adapter.json.JsonProducer;
import tao.features.format.adapter.xml.XmlProducer;

import java.util.Map;

public abstract class ContentTypeFactory {
    public static ContentTypeFactory create(MediaType format) {
        switch (format) {
            case APPLICATION_XML_VALUE:
                return new XmlProducer();
            case TEXT_PLAIN_VALUE:
            case CSV_VALUE:
                return new CsvProducer();
            default:
                return new JsonProducer();
        }
    }

    public abstract String produce(Map<String, Object> items);
}
