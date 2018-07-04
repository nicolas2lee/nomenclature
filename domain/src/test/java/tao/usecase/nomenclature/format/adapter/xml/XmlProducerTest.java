package tao.usecase.nomenclature.format.adapter.xml;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class XmlProducerTest {

    private XmlProducer xmlProducer;

    @Before
    public void setUp() throws Exception {
        xmlProducer = new XmlProducer();
    }

    // TODO: 30/06/2018 need to fix the test
    @Test
    @Ignore
    public void should_return_xml_when_given_json_string() {
        //File file = new File(this.getClass().getClassLoader().getResource("/json/test1.json").getFile());
        Map<String, Object> map = new HashMap<>();
        map.put("countryShortName", "China");
        map.put("countryCodeLen3", "CHN");
        map.put("countryCodeLen2", "CN");
        map.put("countryContinentId", "ASIA");
        map.put("countryLanguage", "CN");
        map.put("language", "CN");
        map.put("id", "1");
        map.put("countryNumCode", "86");

        final String result = xmlProducer.produce(map);
        assertThat(result).isNotBlank();
    }
}