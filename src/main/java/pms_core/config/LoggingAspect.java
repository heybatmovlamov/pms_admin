package pms_core.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Random;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private final HttpServletRequest request;
    private final Random random = new Random();

    // Hər controller method üçün around advice
    @Around("execution(* pms_core.controller..*(..))")
    public Object logControllers(ProceedingJoinPoint joinPoint) throws Throwable {

        // Hər request üçün unikal traceId yaradırıq
        String traceId = "pms-" + String.format("%09d", Math.abs(random.nextLong() % 1_000_000_000));
        MDC.put("traceId", traceId);

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();
        String uri = request.getRequestURI();

        log.info("➡️ Request: {}  | Args: {}",
                uri, Arrays.toString(joinPoint.getArgs()));

        Object result;

        try {
            result = joinPoint.proceed();
        } catch (Exception ex) {
            log.error("❌ Exception in {} | URI: {} |  Message: {}", methodName, uri, ex.getMessage());
            throw ex;
        }
        log.info("✅ Response(short) = {}", safeToString(result));
        // MDC təmizləyirik ki, thread reuse zamanı əvvəlki traceId qalmasın
        MDC.remove("traceId");

        return result;
    }

    String safeToString(Object o) {
        if (o == null) return "null";
        String s = o.toString();
        return s.length() > 1000 ? s.substring(0, 1000) + "..............." : s;
    }
}
