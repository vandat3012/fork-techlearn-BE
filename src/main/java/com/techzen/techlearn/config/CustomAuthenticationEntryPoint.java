package com.techzen.techlearn.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techzen.techlearn.dto.response.ResponseData;
import com.techzen.techlearn.enums.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ResponseData<?> responseData = ResponseData.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .code(ErrorCode.FORBIDDEN_ERROR.getCode())
                .message(ErrorCode.FORBIDDEN_ERROR.getMessage())
                .result(null)
                .build();

        String jsonResponse = objectMapper.writeValueAsString(responseData);

        response.setContentType("application/json");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().write(jsonResponse);
    }
}
