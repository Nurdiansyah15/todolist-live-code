package com.nurd.todolist.models;

public enum Role {
    ROLE_ADMIN,
    ROLE_USER,
    ROLE_SUPER_ADMIN;

    public static boolean isValidRole(String value) {
        if (value == null) return false;
        for (Role status : Role.values()) {
            if (value.equals(status.name())) {
                return true;
            }
        }
        return false;
    }
}
