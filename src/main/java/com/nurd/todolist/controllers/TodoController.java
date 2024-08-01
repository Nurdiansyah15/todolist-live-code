package com.nurd.todolist.controllers;

import com.nurd.todolist.models.Todo;
import com.nurd.todolist.servs.TodoServ;
import com.nurd.todolist.utils.dtos.TodoDto;
import com.nurd.todolist.utils.dtos.formator.ResponseBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoServ todoServ;

    @GetMapping
    public ResponseEntity<?> findAll(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String order,
            @RequestParam(required = false) String page,
            @RequestParam(required = false) String size
    ) {
        return ResponseBuilder.renderPageableJSON(todoServ.findAllByUser(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> findById(@PathVariable String id) {
        return ResponseEntity.ok(todoServ.findById(id));
    }

    @PostMapping
    public ResponseEntity<Todo> create(@Valid @RequestBody TodoDto.Request.Create obj) {
        return new ResponseEntity<>(todoServ.create(obj), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> update(@PathVariable String id, @Valid @RequestBody TodoDto.Request.Update obj) {
        return new ResponseEntity<>(todoServ.update(id, obj), HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Todo> updateStatus(@PathVariable String id, @Valid @RequestBody TodoDto.Request.UpdateStatus obj) {
        return new ResponseEntity<>(todoServ.updateStatus(id, obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        todoServ.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
