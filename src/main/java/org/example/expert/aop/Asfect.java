package org.example.expert.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AspectLogging {

    private final HttpServletRequest request;

    public AspectLogging(HttpServletRequest request) {
        this.request = request;
    }

    @Pointcut("execution(* org.example.expert.domain.comment.controller.CommentAdminController.*(..))")
    private void commentAdminControllerMethods() {}

    @Pointcut("execution(* org.example.expert.domain.user.controller.UserAdminController.*(..))")
    private void userAdminControllerMethods() {}



    @Before("commentAdminControllerMethods() || userAdminControllerMethods() ")
    public void logAdminAccess(JoinPoint joinPoint) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication != null ? authentication.getName() : "Anonymous";

        String requestUrl = request.getRequestURL().toString();
        LocalDateTime requestTime = LocalDateTime.now();

        // 메소드 호출 전 로그
        log.info("Admin API Access Log 실행 전: 요청한 사용자 ID = {}, API 요청 시각 = {}, API 요청 URL = {}",
                userId, requestTime, requestUrl);
    }
}
