package com.nurd.todolist.controllers;

import com.nurd.todolist.servs.TodoServ;
import com.nurd.todolist.servs.UserServ;
import com.nurd.todolist.utils.dtos.UserDto;
import com.nurd.todolist.utils.dtos.formator.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    private UserServ userServ;

    @Autowired
    private TodoServ todoServ;

    @GetMapping("/users")
    public ResponseEntity<?> findAllUsers(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) String page,
            @RequestParam(required = false) String size
    ) {
        return ResponseBuilder.renderPageableJSON(userServ.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> findUserById(@PathVariable String id) {
        return ResponseEntity.ok(userServ.findById(id));
    }

    @PatchMapping("/users/{id}/role")
    public ResponseEntity<?> updateUserRole(@PathVariable String id, @RequestBody UserDto.Request.UpdateRole obj) {
        return ResponseEntity.ok(userServ.updateUserRole(id, obj));
    }


    @GetMapping("/todos")
    public ResponseEntity<?> findAllTodos(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String order,
            @RequestParam(required = false) String page,
            @RequestParam(required = false) String size
    ) {
        return ResponseBuilder.renderPageableJSON(todoServ.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<?> findTodoById(@PathVariable String id) {
        return ResponseEntity.ok(todoServ.findByIdMoreDetails(id));
    }

}
