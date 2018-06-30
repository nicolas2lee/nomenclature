package tao.handler;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


// TODO: 25/06/2018 need to fix how to test functional endpoint
//@RunWith(SpringRunner.class)
//@WebFluxTest(NomenclatureHandler.class)
//@WebAppConfiguration
//@WebMvcTest(NomenclatureHandler.class)
//@Import({NomenclatureServiceImpl.class, SqlHelperImpl.class})
public class NomenclatureHandlerITTest {

    @Before
    public void setUp() throws Exception {
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void should_return_success_code() throws Exception {
//        webClient.getAll().uri("/api/v1/pays").exchange()
//                .expectStatus().isOk();
//        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/pays"))
//                .andExpect(status().isOk());
    }
}