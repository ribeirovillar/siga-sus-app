package com.app.gateway.filter;

import com.app.gateway.service.GrpcTokenService;
import com.grpc.token.ValidateTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class TokenValidationGatewayFilterFactory extends AbstractGatewayFilterFactory<TokenValidationGatewayFilterFactory.Config> {

    private static final Logger logger = LoggerFactory.getLogger(TokenValidationGatewayFilterFactory.class);
    private final GrpcTokenService grpcTokenService;

    public TokenValidationGatewayFilterFactory(GrpcTokenService grpcTokenService) {
        super(Config.class);
        this.grpcTokenService = grpcTokenService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getPath().value();

            if (path.contains("/swagger-ui/") || path.contains("/v3/api-docs") || path.contains("/health")) {
                logger.debug("Skipping token validation for auth route: {}", path);
                return chain.filter(exchange);
            }

            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                logger.warn("Missing or invalid Authorization header for path: {}", path);
                return unauthorizedResponse(exchange);
            }

            String token = authHeader.substring(7);

            try {
                ValidateTokenResponse response = grpcTokenService.validateToken(token);

                if (!response.getValid()) {
                    logger.warn("Invalid token for path: {}. Error: {}", path, response.getErrorMessage());
                    return unauthorizedResponse(exchange);
                }

                ServerWebExchange modifiedExchange = exchange.mutate()
                    .request(exchange.getRequest().mutate()
                        .header("X-User-Username", response.getUsername())
                        .header("X-User-Role", response.getRole())
                        .build())
                    .build();

                logger.debug("Token validated successfully for user: {} with role: {}",
                    response.getUsername(), response.getRole());

                return chain.filter(modifiedExchange);

            } catch (Exception e) {
                logger.error("Error validating token for path: {}", path, e);
                return unauthorizedResponse(exchange);
            }
        };
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    public static class Config {
        // Configuration properties can be added here if needed
    }
}