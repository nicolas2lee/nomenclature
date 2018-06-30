package tao.usecase.nomenclature.format.adapter.json;

import org.json.simple.JSONObject;
import tao.usecase.nomenclature.format.adapter.ContentTypeFactory;
import tao.usecase.nomenclature.format.adapter.MediaType;

import java.util.Map;

public class JsonProducer extends ContentTypeFactory {
    @Override
    public String produce(Map<String, Object> items) {
        super.setHttpContentTypeHeader(MediaType.APPLICATION_JSON_VALUE.getValue());
        return JSONObject.toJSONString(items);
    }
}
