package com.app.interceptors;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.app.annotation.InternalServiceOnly;
import com.app.exception.auth.AuthExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class InternalServiceOnlyAspect {

    private final static String INTERNAL_SERVICE_HEADER = "X-Internal-Service";
    private final static String AUTH_SERVICE_ID_VALUE = "auth-service";
    private final static String USER_SERVICE_ID_VALUE = "user-service";
    private final static String PATIENT_RECORD_SERVICE_ID_VALUE = "patient-record-service";
    private final static String TRIAGE_ORCHESTRATOR_SERVICE_ID_VALUE = "triage-orchestrator-service";
    private final static String CLINICAL_RULE_ENGINE_SERVICE_ID_VALUE = "clinical-rule-engine-service";
    private final static String API_GATEWAY_ID_VALUE = "api-gateway";

    private final static String[] INTERNAL_SERVICE_IDS = {
            AUTH_SERVICE_ID_VALUE,
            USER_SERVICE_ID_VALUE,
            PATIENT_RECORD_SERVICE_ID_VALUE,
            TRIAGE_ORCHESTRATOR_SERVICE_ID_VALUE,
            CLINICAL_RULE_ENGINE_SERVICE_ID_VALUE,
            API_GATEWAY_ID_VALUE
    };

    @Around("@annotation(internalServiceOnly)")
    public Object checkInternalService(ProceedingJoinPoint joinPoint, InternalServiceOnly internalServiceOnly)
            throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        String internalServiceHeader = request.getHeader(INTERNAL_SERVICE_HEADER);

        if (internalServiceHeader == null || !Arrays.asList(INTERNAL_SERVICE_IDS).contains(internalServiceHeader)) {
            throw AuthExceptionHandler.unauthorized();
        }

        return joinPoint.proceed();
    }
}