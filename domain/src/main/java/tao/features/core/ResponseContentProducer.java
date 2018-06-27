package tao.features.core;

import java.util.List;
import java.util.Map;

public interface ResponseContentProducer {
    void create(List<String> headers);

    String produce(Map<String, Object> map);
}
