package com.mitocode.microservice.cloud_gateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.Optional;

@Component
public class GlobalFilters implements GlobalFilter{

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String cookieResponse;

        Optional<String> mitocodeToken = Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("MITOCODE_TOKEN"));

        if(mitocodeToken.isPresent()){
            cookieResponse = mitocodeToken.get();
        }else{
            cookieResponse = "sinCookie";
        }


        //exchange.getRequest().mutate().header("token", cookieResponse);
        //exchange.getRequest().getQueryParams().add("flag", cookieResponse);


        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                    //exchange.getResponse().getCookies().add("TOKEN", ResponseCookie.from("MITOCODE_TOKEN",cookieResponse).build());
                }
        ));
    }
}
