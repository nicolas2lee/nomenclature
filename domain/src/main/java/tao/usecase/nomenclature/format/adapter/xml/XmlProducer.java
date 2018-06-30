package tao.usecase.nomenclature.format.adapter.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tao.usecase.nomenclature.format.adapter.ContentTypeFactory;
import tao.usecase.nomenclature.format.adapter.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XmlProducer extends ContentTypeFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(XmlProducer.class);

    // TODO: 30/06/2018 should test for this example

    /**
     * {"countryShortName":"China","countryCodeLen3":"CHN","countryCodeLen2":"CN","countryContinentId":"ASIA","countryLanguage":"CN","language":"CN","id":"1","countryNumCode":"86"}
     */
    @Override
    public String produce(Map<String, Object> items) {
        super.setHttpContentTypeHeader(MediaType.APPLICATION_XML_VALUE.getValue());
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
