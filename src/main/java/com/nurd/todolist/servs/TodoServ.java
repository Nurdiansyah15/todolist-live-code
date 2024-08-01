package com.nurd.todolist.servs;

import com.nurd.todolist.models.Status;
import com.nurd.todolist.models.Todo;
import com.nurd.todolist.models.User;
import com.nurd.todolist.repos.TodoRepo;
import com.nurd.todolist.utils.dtos.TodoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class TodoServ {

    @Autowired
    private AuthServ authServ;

    @Autowired
    private TodoRepo todoRepo;

    public Todo create(TodoDto.Request.Create obj) {

        User user = authServ.getUserAuthenticated();

        return todoRepo.save(Todo.builder()
                .title(obj.getTitle())
                .description(obj.getDescription())
                .dueDate(obj.getDueDate())
                .createdAt(new Date())
                .status(Status.PENDING)
                .user(user)
                .build());
    }

    public Page<Todo> findAll(Pageable pageable) {
        return todoRepo.findAll(pageable);
    }

    public Page<Todo> findAllByUser(Pageable pageable) {
        User user = authServ.getUserAuthenticated();
        return todoRepo.findAllByUser(user.getId(), pageable);
    }

    public Todo findById(String id) {
        User user = authServ.getUserAuthenticated();
        Todo todo = todoRepo.findById(UUID.fromString(id)).orElseThrow(() -> new IllegalArgumentException("Todo with id " + id + " not found"));
        if (todo.getUser() == null || !todo.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("Todo with id " + id + " can't be accessed");
        }
        return todo;
    }

    public TodoDto.Response.Details findByIdMoreDetails(String id) {
        Todo todo = todoRepo.findById(UUID.fromString(id)).orElseThrow(() -> new IllegalArgumentException("Todo with id " + id + " not found"));
        return TodoDto.Response.Details.builder()
                .id(todo.getId().toString())
                .userId(todo.getUser() == null ? null : todo.getUser().getId().toString())
                .userName(todo.getUser() == null ? null : todo.getUser().getUsername())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .dueDate(todo.getDueDate())
                .status(todo.getStatus().toString())
                .createdAt(todo.getCreatedAt())
                .build();
    }

    public Todo update(String id, TodoDto.Request.Update obj) {
        User user = authServ.getUserAuthenticated();

        Todo todo = todoRepo.findById(UUID.fromString(id)).orElseThrow(() -> new IllegalArgumentException("Todo with id " + id + " not found"));
        if (!todo.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("Todo with id " + id + " can't be accessed");
        }

        todo.setTitle(obj.getTitle());
        todo.setDescription(obj.getDescription());
        todo.setDueDate(obj.getDueDate());
        todo.setUser(user);

        if (!Status.isValidStatus(obj.getStatus().toUpperCase())) {
            throw new IllegalArgumentException("Status that you want not found");
        }
        todo.setStatus(Status.valueOf(obj.getStatus().toUpperCase()));

        return todoRepo.save(todo);
    }

    public Todo updateStatus(String id, TodoDto.Request.UpdateStatus obj) {
        User user = authServ.getUserAuthenticated();
        Todo todo = todoRepo.findById(UUID.fromString(id)).orElseThrow(() -> new IllegalArgumentException("Todo with id " + id + " not found"));
        if (!todo.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Todo does not have user");
        }
        if (!Status.isValidStatus(obj.getStatus().toUpperCase())) {
            throw new IllegalArgumentException("Status that you want not found");
        }
        todo.setStatus(Status.valueOf(obj.getStatus().toUpperCase()));
        return todoRepo.save(todo);
    }

    public void deleteById(String id) {
        todoRepo.deleteById(UUID.fromString(id));
    }
}
