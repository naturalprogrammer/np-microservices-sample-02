package com.naturalprogrammer.np02.gateway;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.naturalprogrammer.np02.gateway.dto.MyToken;
import com.naturalprogrammer.spring.lemon.commons.util.LecUtils;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthorizationTokenConvertionFilter implements WebFilter {
	
	private static final Log log = LogFactory.getLog(AuthorizationTokenConvertionFilter.class);
	
	private AuthClient authClient;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		
		String token = getAuthHeader(exchange);
		
		log.debug("Original token: " + token);
			
		if(token == null || !token.startsWith(LecUtils.TOKEN_PREFIX))
			return chain.filter(exchange);
		
		String microToken = convert(token);

		log.debug("Microtoken: " + microToken);
		
		//ServerHttpRequest changedRequest = exchange.getRequest().mutate().headers(headersConsumer);
		
		exchange = exchange.mutate().request(
				exchange.getRequest().mutate().headers(
						headers -> headers.set(HttpHeaders.AUTHORIZATION, microToken)				
				).build())
			.build();
		
		log.debug("Changed token: " + getAuthHeader(exchange));

		return chain.filter(exchange);
	}

	private String convert(String token) {
		
		MyToken myToken = authClient.fetchFullToken(token);
		return myToken.getToken();
	}
	
	private String getAuthHeader(ServerWebExchange exchange) {
		
		List<String> tokens = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
		
		if (tokens.size() == 0)
			return null;
		
		if (tokens.size() > 1)
			throw new RuntimeException("Duplicate tokens" + tokens);
		
		return tokens.get(0);
	}
	
//	private ServerHttpRequest updatedRequest(ServerHttpRequest request, String newToken) {
//		
//		HttpHeaders headers = request.getHeaders();
//		headers.set(HttpHeaders.AUTHORIZATION, newToken);
//		return request.mutate().
//	}
}
