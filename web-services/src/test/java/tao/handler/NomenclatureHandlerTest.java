package tao.handler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;


// TODO: 25/06/2018 need to fix how to test functional endpoint
@RunWith(SpringRunner.class)
@WebFluxTest(NomenclatureHandler.class)
public class NomenclatureHandlerTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    public void should_return_success_code() {
        webClient.get().uri("/api/v1/pays").exchange()
                .expectStatus().isOk();
    }
}