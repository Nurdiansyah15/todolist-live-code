package com.nurd.todolist.utils.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class UserDto {

    public static class Request {

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class CreateAdmin {

            @NotNull
            private String username;
            @NotNull
            @Email
            private String email;
            @NotNull
            private String password;

        }

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class UpdateRole {

            @NotNull
            private String role;
        }
    }

}
