package com.example.security.interceptor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean notAuthenticated = "anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!notAuthenticated) {
            response.sendRedirect("/");
        }

        return notAuthenticated;
    }
}
