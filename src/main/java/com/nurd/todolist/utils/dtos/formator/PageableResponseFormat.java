package com.nurd.todolist.utils.dtos.formator;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageableResponseFormat<T> {
    private List<T> items;
    private Long totalItems;
    private Integer currentPage;
    private Integer totalPages;
}
