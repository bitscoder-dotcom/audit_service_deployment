package org.segura.auditserviceproducer.interceptor;
/*
 * Created by Daniel - 18/11/2024 (19:34)
 */

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.segura.auditserviceproducer.model.AuditLog;
import org.segura.auditserviceproducer.service.AuditKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.time.LocalDateTime;

/***
 * Logs API call details and sends them to Kafka for auditing
 */


@Component
public class AuditInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuditInterceptor.class);
    private final AuditKafkaProducer auditKafkaProducer;

    public AuditInterceptor(AuditKafkaProducer auditKafkaProducer) {
        this.auditKafkaProducer = auditKafkaProducer;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }
        if (!(response instanceof ContentCachingResponseWrapper)) {
            response = new ContentCachingResponseWrapper(response);
        }
        logger.debug("Intercepted method: {}", handler);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // get user details from security context
        String email = "anonymous";
        String userId = request.getRemoteUser();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        }

        // get api endpoint and Http method
        String apiEndpoint = request.getRequestURI();
        String crudOperation = request.getMethod();
        String responseStatus = String.valueOf(response.getStatus());

        String requestBody = "No request body";
        try {
            if (request instanceof ContentCachingRequestWrapper) {
                ContentCachingRequestWrapper catchingRequest = (ContentCachingRequestWrapper) request;
                byte[] content = catchingRequest.getContentAsByteArray();
                requestBody = content.length > 0 ? new String(content, request.getCharacterEncoding()) : requestBody;
            }
        } catch (Exception e) {
            logger.error("Error reading request body: {}", e.getMessage());
        }

        String responseBody = "No response body";
        try {
            if (response instanceof ContentCachingResponseWrapper) {
                ContentCachingResponseWrapper cachingResponse = (ContentCachingResponseWrapper) response;
                byte[] content = cachingResponse.getContentAsByteArray();
                responseBody = content.length > 0 ? new String(content, response.getCharacterEncoding()) : responseBody;
                cachingResponse.copyBodyToResponse();
            }
        } catch (Exception e) {
            logger.error("Error reading response body: {}", e.getMessage());
        }

        // create and send audit log
        AuditLog auditLog = new AuditLog();
        auditLog.setEmail(email);
        auditLog.setUserId(userId);
        auditLog.setApiEndpoint(apiEndpoint);
        auditLog.setCrudOperation(crudOperation);
        auditLog.setResponse(responseStatus);
        auditLog.setDataBefore(requestBody);
        auditLog.setDataAfter(responseBody);
        auditLog.setTimestamp(LocalDateTime.now());

        logger.info("Audit log created: {}", auditLog);

        auditKafkaProducer.sendAuditLog(auditLog);
    }
}






/*
@Aspect
@Component
public class AuditAspect {

    private static final Logger logger = LoggerFactory.getLogger(AuditAspect.class);

    private final KafkaProducerService kafkaProducerService;

    private  HttpServletRequest request;

    private  HttpServletResponse response;

    public AuditAspect(KafkaProducerService kafkaProducerService, HttpServletRequest request, HttpServletResponse response) {
        this.kafkaProducerService = kafkaProducerService;
        this.request = request;
        this.response = response;
    }

    @Pointcut("execution(* org.segura..controller.*(..))") // finds controller methods in the controller package
    public void controllerMethods() {}

    @Before("controllerMethods()")
    public void logBeforeExecution(JoinPoint joinPoint) {
        logger.debug("Intercepted method: {}", joinPoint.getSignature().getName());
    }


    // to get the request parameters and payload before controller method executes
    @Before("controllerMethods()")
    public void wrapRequestAndResponse(JoinPoint joinPoint) {
        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }
        if (!(response instanceof ContentCachingResponseWrapper)) {
            response = new ContentCachingResponseWrapper(response);
        }
    }

    @AfterReturning(pointcut = "controllerMethods()", returning = "result")
    public void auditApiCall(JoinPoint joinPoint, Object result) {
        // get user details from security context
        String email = "anonymous";
        String userId = request.getRemoteUser();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        }

        // get api endpoint and Http method
        String apiEndpoint = request.getRequestURI();
        String crudOperation = request.getMethod();
        String responseStatus = String.valueOf(response.getStatus());

        String requestBody = "No request body";
        try {
            if (request instanceof ContentCachingRequestWrapper) {
                ContentCachingRequestWrapper catchingRequest = (ContentCachingRequestWrapper) request;
                byte[] content = catchingRequest.getContentAsByteArray();
                requestBody = content.length > 0 ? new String(content, request.getCharacterEncoding()) : requestBody;
            }
        } catch (Exception e) {
            logger.error("Error reading request body: {}", e.getMessage());
        }

        String responseBody = "No response body";
        try {
            if (response instanceof ContentCachingResponseWrapper) {
                ContentCachingResponseWrapper cachingResponse = (ContentCachingResponseWrapper) response;
                byte[] content = cachingResponse.getContentAsByteArray();
                responseBody = content.length > 0 ? new String(content, response.getCharacterEncoding()) : responseBody;
                cachingResponse.copyBodyToResponse();
            }
        } catch (Exception e) {
            logger.error("Error reading response body: {}", e.getMessage());
        }


        // create and send audit log
        AuditLog auditLog = new AuditLog();
        auditLog.setEmail(email);
        auditLog.setUserId(userId);
        auditLog.setApiEndpoint(apiEndpoint);
        auditLog.setCrudOperation(crudOperation);
        auditLog.setResponse(responseStatus);
        auditLog.setDataBefore(requestBody);
        auditLog.setDataAfter(responseBody);
        auditLog.setTimestamp(LocalDateTime.now());

        logger.info("Audit log created: {}", auditLog);

        kafkaProducerService.sendAuditLog(auditLog);
    }
}

 */
