package com.nurd.todolist.models;

public enum Status {
    PENDING,
    COMPLETED,
    IN_PROGRESS;

    public static boolean isValidStatus(String value) {
       if (value == null) return false;
       for (Status status : Status.values()) {
           if (status.name().equals(value)) {
               return true;
           }
       }
       return false;
    }
}
