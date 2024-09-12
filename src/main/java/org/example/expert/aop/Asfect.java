package org.example.expert.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class Asfect {

    private final HttpServletRequest request;
    private final Pointcuts pointcuts;

    public Asfect(HttpServletRequest request, Pointcuts pointcuts) {
        this.request = request;
        this.pointcuts = pointcuts;
    }

    @Before("pointcuts.adminControllerMethods()")
//    logAdminAccess : 어드바이스, ProceedingJoinPoin : 조인포인트
    public Object logAdminAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication != null ? authentication.getName() : "Anonymous";

        String requestUrl = request.getRequestURL().toString();
        LocalDateTime requestTime = LocalDateTime.now();

//        메소드 호출 전 로그
        log.info("Admin API Access Log 실행 전: 요청한 사용자 ID = {}, API 요청 시각 = {}, API 요청 URL = {}",
                userId, requestTime, requestUrl);

        Object result = joinPoint.proceed();
//        메소드 호출 후 로그
        log.info("Admin API Access Log - 실행 후: 요청한 사용자 ID = {}, API 요청 시각 = {}, API 요청 URL = {}",
                userId, requestTime, requestUrl);

        return result;
    }
}