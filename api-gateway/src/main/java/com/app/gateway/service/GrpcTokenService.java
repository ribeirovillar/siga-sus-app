package com.app.gateway.service;

import com.grpc.token.TokenValidationServiceGrpc;
import com.grpc.token.ValidateTokenRequest;
import com.grpc.token.ValidateTokenResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GrpcTokenService {

    @GrpcClient("token-validation")
    private TokenValidationServiceGrpc.TokenValidationServiceBlockingStub tokenValidationServiceBlockingStub;

    private static final Logger logger = LoggerFactory.getLogger(GrpcTokenService.class);

    public ValidateTokenResponse validateToken(String token) {
        if (token == null) {
            logger.error("Token is null in validateToken request");
            throw new IllegalArgumentException("Token must not be null");
        }

        if (tokenValidationServiceBlockingStub == null) {
            logger.error("gRPC stub is not injected (tokenValidationServiceBlockingStub is null)");
            throw new IllegalStateException("gRPC stub is not injected");
        }
        return tokenValidationServiceBlockingStub.validateToken(ValidateTokenRequest.newBuilder().setToken(token).build());
    }
}
