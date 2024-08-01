package com.nurd.todolist.utils.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

public class TodoDto {

    public static class Request {

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class Create {

            @NotNull
            private String title;
            @NotNull
            private String description;
            @NotNull
            @JsonFormat(pattern = "yyyy-MM-dd")
            private Date dueDate;

            private String userId;

        }

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class Update {
            @NotNull
            private String title;
            @NotNull
            private String description;
            @NotNull
            @JsonFormat(pattern = "yyyy-MM-dd")
            private Date dueDate;
            @NotNull
            private String status;

            private String userId;
        }

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class UpdateStatus {
            @NotNull
            private String status;
        }

    }


    public static class Response {

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class Common {

            private String id;
            private String title;
            private String description;
            private Date dueDate;
            private Date createdAt;
            private String status;

        }


        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class Details {
            private String id;
            private String userId;
            private String userName;
            private String title;
            private String description;
            private Date dueDate;
            private Date createdAt;
            private String status;
        }
    }
}
