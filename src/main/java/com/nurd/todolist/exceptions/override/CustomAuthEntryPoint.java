package com.nurd.todolist.exceptions.override;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nurd.todolist.utils.dtos.formator.ErrorResponseFormat;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(response(authException.getMessage()));
    }

    private String response(String message) throws IOException {
        return objectMapper.writeValueAsString(ErrorResponseFormat.builder().message(message).error(HttpStatus.UNAUTHORIZED.getReasonPhrase()).build());
    }
}
