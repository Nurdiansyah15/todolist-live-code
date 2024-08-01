package com.nurd.todolist.exceptions.override;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
