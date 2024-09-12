package org.example.expert.aop;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
public class Pointcuts {

    @Pointcut("execution(* org.example.expert.domain.comment.controller.CommentAdminController.*(..))")
    public void commentAdminControllerMethods() {}

    @Pointcut("execution(* org.example.expert.domain.user.controller.UserAdminController.*(..))")
    public void userAdminControllerMethods() {}

    @Pointcut("commentAdminControllerMethods() || userAdminControllerMethods()")
    public void adminControllerMethods() {}
}