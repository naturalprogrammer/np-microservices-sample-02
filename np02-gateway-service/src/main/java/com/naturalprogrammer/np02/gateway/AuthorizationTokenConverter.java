package com.naturalprogrammer.np02.gateway;

import java.util.Map;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.naturalprogrammer.spring.lemon.commons.util.LecUtils;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthorizationTokenConverter implements WebFilter {
	
	private AuthClient authClient;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		
		String token = exchange.getRequest()
				.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
			
		if(token == null || !token.startsWith(LecUtils.TOKEN_PREFIX))
			return chain.filter(exchange);
		
		String microToken = convert(token);

		exchange = exchange.mutate().request(
			exchange.getRequest().mutate().header(HttpHeaders.AUTHORIZATION, microToken).build())
			.build();
		
		return chain.filter(exchange);
	}

	private String convert(String token) {
		
		Map<String, String> tokenMap = authClient.fetchMicroToken();
		return tokenMap.get("token");
	}
}
