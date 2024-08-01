package com.nurd.todolist.utils.dtos.formator;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {
    public static <T> ResponseEntity<?> renderJSON(T data, String message, HttpStatus httpStatus) {
        ResponseFormat<T> response = ResponseFormat.<T>builder()
                .message(message)
                .status(httpStatus.getReasonPhrase())
                .data(data)
                .build();
        return ResponseEntity.status(httpStatus).body(response);
    }

    public static <T> ResponseEntity<?> renderError(T message, HttpStatus httpStatus) {
        ErrorResponseFormat<String> response = ErrorResponseFormat.<String>builder()
                .error( message.toString().toLowerCase())
                .status(httpStatus.getReasonPhrase())
                .build();
        return ResponseEntity.status(httpStatus).body(response);
    }

    public static <T> ResponseEntity<?> renderPageableJSON(Page<T> page, HttpStatus httpStatus) {
        PageableResponseFormat<T> response = PageableResponseFormat.<T>builder()
                .items(page.getContent())
                .totalItems(page.getTotalElements())
                .currentPage(page.getNumber())
                .totalPages(page.getTotalPages())
                .build();
        return ResponseEntity.status(httpStatus).body(response);
    }

}
