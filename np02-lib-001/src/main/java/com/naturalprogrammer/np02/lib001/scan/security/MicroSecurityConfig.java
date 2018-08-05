package com.naturalprogrammer.np02.lib001.scan.security;

import java.util.function.Function;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.AbstractSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;

import com.naturalprogrammer.spring.lemon.commons.security.JwtAuthenticationToken;
import com.naturalprogrammer.spring.lemon.commons.security.JwtService;
import com.naturalprogrammer.spring.lemon.commons.util.LecUtils;
import com.naturalprogrammer.spring.lemon.exceptions.util.LexUtils;
import com.nimbusds.jwt.JWTClaimsSet;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Configuration
@ConditionalOnMissingClass("com.naturalprogrammer.spring.lemonreactive.LemonReactiveAutoConfiguration")
@EnableReactiveMethodSecurity
@AllArgsConstructor
public class MicroSecurityConfig {
	
	private static final Log log = LogFactory.getLog(MicroSecurityConfig.class);
	
	private JwtService jwtService;

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,
			AbstractSecurityExpressionHandler<?> expressionHandler,
			PermissionEvaluator permissionEvaluator) {
		
		log.info("Configuring SecurityWebFilterChain ...");
		
		expressionHandler.setPermissionEvaluator(permissionEvaluator);
		
		return http
				.authorizeExchange()
					.anyExchange().permitAll()
				.and()
					.securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
				.exceptionHandling()
					.accessDeniedHandler((exchange, exception) -> Mono.error(exception))
					.authenticationEntryPoint((exchange, exception) -> Mono.error(exception))
				.and()
					.csrf().disable()
					.addFilterAt(tokenAuthenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
				.logout().disable()
				.build();
	}

	private AuthenticationWebFilter tokenAuthenticationFilter() {
		
		AuthenticationWebFilter filter = new AuthenticationWebFilter(tokenAuthenticationManager());		
		filter.setAuthenticationConverter(tokenAuthenticationConverter());
		filter.setAuthenticationFailureHandler((exchange, exception) -> Mono.error(exception));
		
		return filter;
	}

	private Function<ServerWebExchange, Mono<Authentication>> tokenAuthenticationConverter() {
		
		return serverWebExchange -> {
			
			String authorization = serverWebExchange.getRequest()
				.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
			
			if(authorization == null || !authorization.startsWith(LecUtils.TOKEN_PREFIX))
				return Mono.empty();

			return Mono.just(new JwtAuthenticationToken(authorization.substring(7)));		
		};
	}

	private ReactiveAuthenticationManager tokenAuthenticationManager() {
		
		return authentication -> {
			
			log.debug("Authenticating with token ...");

			String token = (String) authentication.getCredentials();
			
			JWTClaimsSet claims = jwtService.parseToken(token, JwtService.AUTH_AUDIENCE);
			
			MicroUserDetails userDetails = LecUtils.deserialize((String)claims.getClaim(JwtService.USER_CLAIM));
			
			if (userDetails == null)
				return Mono.error(new AuthenticationCredentialsNotFoundException(LexUtils.getMessage("com.naturalprogrammer.spring.userNotFound", "")));
			
			return Mono.just(new JwtAuthenticationToken(userDetails, token, userDetails.getAuthorities()));
		};
	}

}
