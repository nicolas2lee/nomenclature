package tao.features.format.adapter.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tao.features.format.adapter.ContentTypeFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XmlProducer extends ContentTypeFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(XmlProducer.class);

    @Override
    public String produce(Map<String, Object> items) {
        String type;
        List<String> finalString = new ArrayList<>();
        finalString.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

        List<Map> elements;
        for (String key : items.keySet()) {
            type = items.get(key).getClass().getSimpleName();
            LOGGER.warn("Type >>" + type);
            if (type.equals("Integer")) {
                //Le sommaire
                finalString.add("<" + key + ">" + items.get(key) + "</" + key + ">");

            } else {
                //Les éléments
                LOGGER.warn(">>><" + items.get(key).toString());
                elements = (ArrayList) items.get(key);
                finalString.add("<items>");
                for (Map element : elements) {
                    finalString.add("<item>");
                    for (Object k : element.keySet()) {
                        finalString.add("<" + k + ">" + element.get(k).toString() + "</" + k + ">");
                    }
                    finalString.add("</item>");
                }
                finalString.add("</items>");
            }
        }

        return String.join("", finalString);
    }
}
