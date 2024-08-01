package com.nurd.todolist.controllers;

import com.nurd.todolist.servs.UserServ;
import com.nurd.todolist.utils.dtos.UserDto;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CreateAdminController {

    private final UserServ userServ;

    @PostMapping("/super-admin")
    public ResponseEntity<?> createSuperAdmin(@RequestBody UserDto.Request.CreateAdmin obj) {
        return ResponseEntity.ok(userServ.createSuperAdmin(obj));
    }
}
