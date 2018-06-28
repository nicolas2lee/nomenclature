package tao.config.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import tao.handler.NomenclatureHandler;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Created by xinrui on 11/12/2017.
 */
@Configuration
public class Routes {

    @Bean
    public RouterFunction<?> routerFunction(NomenclatureHandler nomenclatureHandler) {
        return nest(path("/api/v1"),
                route(
                        GET("/welcome").and(accept(MediaType.APPLICATION_JSON)),
                        request -> ServerResponse.ok().body(fromObject("welcome"))

                )
                .and(route(
                        GET("/{nomenclatureName}"),
                        nomenclatureHandler::list
                ))
                        .and(route(
                                GET("/{nomenclatureName}/{id}"),
                                nomenclatureHandler::show
                        ))

        );
    }
}
