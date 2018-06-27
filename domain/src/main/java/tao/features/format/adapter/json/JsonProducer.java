package tao.features.format.adapter.json;

import org.json.simple.JSONObject;
import tao.features.format.adapter.ContentTypeFactory;

import java.util.Map;

public class JsonProducer extends ContentTypeFactory {
    @Override
    public String produce(Map<String, Object> items) {
        return JSONObject.toJSONString(items);
    }
}
