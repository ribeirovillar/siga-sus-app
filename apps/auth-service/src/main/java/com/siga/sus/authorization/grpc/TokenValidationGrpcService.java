package com.siga.sus.authorization.grpc;

import com.siga.sus.authorization.security.JwtTokenProvider;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class TokenValidationGrpcService extends TokenValidationServiceGrpc.TokenValidationServiceImplBase {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void validateToken(ValidateTokenRequest request, StreamObserver<ValidateTokenResponse> responseObserver) {
        log.info("Recebida requisição gRPC nativa para validar token");

        try {
            String token = request.getToken();
            boolean isValid = jwtTokenProvider.validateToken(token);

            ValidateTokenResponse.Builder responseBuilder = ValidateTokenResponse.newBuilder()
                    .setValid(isValid);

            if (isValid) {
                String username = jwtTokenProvider.getUsernameFromToken(token);
                String role = jwtTokenProvider.getRoleFromToken(token);
                responseBuilder
                        .setUsername(username)
                        .setRole(role);
                log.info("Token válido - Usuário: {}, Role: {}", username, role);
            } else {
                responseBuilder.setErrorMessage("Token inválido");
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            log.error("Erro ao validar token via gRPC nativo", e);

            ValidateTokenResponse errorResponse = ValidateTokenResponse.newBuilder()
                    .setValid(false)
                    .setErrorMessage("Erro na validação: " + e.getMessage())
                    .build();

            responseObserver.onNext(errorResponse);
            responseObserver.onCompleted();
        }
    }
}