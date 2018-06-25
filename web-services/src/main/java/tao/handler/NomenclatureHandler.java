package tao.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tao.core.NomenclatureService;
import tao.core.model.Nomenclature;

import java.util.Optional;

@Component
public class NomenclatureHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(NomenclatureHandler.class);

    private final NomenclatureService nomenclatureService;

    NomenclatureHandler(NomenclatureService nomenclatureService){
        this.nomenclatureService = nomenclatureService;
    }

    public Mono<ServerResponse> list(final ServerRequest request){
        LOGGER.info(String.format("%s %s", request.methodName(), request.path()));
        Nomenclature nomenclature = nomenclatureService.getNomenclature(request.pathVariable("nomenclatureName"))
                .orElse(Nomenclature.NONE);

        Optional<String> selectedFields = request.queryParam("sortField");
        Optional<String> sortDirections = request.queryParam("sortDirections");
        Optional<String> pagingPacket = request.queryParam("pagingPacket");
        Optional<String> offset = request.queryParam("offset");

        return ServerResponse.ok().body(Flux.range(1, 1000000000).map(x-> String.valueOf(x)), String.class);
    }
}
