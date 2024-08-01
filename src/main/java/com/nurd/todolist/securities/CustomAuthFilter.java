package com.nurd.todolist.securities;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CustomAuthFilter extends OncePerRequestFilter {

    @Value("${admin.secret.key}")
    String adminSecretKey;
    @Value("${super.admin.secret.key}")
    String superAdminSecretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        AntPathMatcher pathMatcher = new AntPathMatcher();
        boolean urlEditRole = pathMatcher.match("/api/admin/users/**/role", request.getRequestURI());
        boolean urlSuperAdminCreation = pathMatcher.match("/api/super-admin", request.getRequestURI());

        if (request.getMethod().equals("PATCH") && urlEditRole) {

            if (!request.getHeader("X-Admin-Secret-Key").equals(adminSecretKey)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        if (request.getMethod().equals("POST") && urlSuperAdminCreation) {

            if (!request.getHeader("X-Super-Admin-Secret-Key").equals(superAdminSecretKey)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        filterChain.doFilter(request, response);

    }


}
