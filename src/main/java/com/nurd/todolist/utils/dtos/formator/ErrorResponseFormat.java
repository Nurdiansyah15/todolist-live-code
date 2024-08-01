package com.nurd.todolist.utils.dtos.formator;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponseFormat<T> {
    private String status;
    private T error;
}
