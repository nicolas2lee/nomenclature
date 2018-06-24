package tao.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class NomenclatureHandler {

    public Mono<ServerResponse> list(final ServerRequest request){
        return ServerResponse.ok().body(Flux.range(1, 1000000000).map(x-> String.valueOf(x)), String.class);
    }
}
