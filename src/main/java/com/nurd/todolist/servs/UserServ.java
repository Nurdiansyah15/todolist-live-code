package com.nurd.todolist.servs;

import com.nurd.todolist.models.Role;
import com.nurd.todolist.models.User;
import com.nurd.todolist.repos.UserRepo;
import com.nurd.todolist.utils.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServ {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page<User> findAll(Pageable pageable) {
        return userRepo.findAll(pageable);
    }

    public User findById(String id) {
        return userRepo.findById(UUID.fromString(id)).orElseThrow(() -> new IllegalArgumentException("User with id " + id + " not found"));
    }

    public User updateUserRole(String id, UserDto.Request.UpdateRole obj) {
        User user = userRepo.findById(UUID.fromString(id)).orElseThrow(() -> new IllegalArgumentException("User with id " + id + " not found"));
        user.setRole(Role.isValidRole("ROLE_" + obj.getRole().toUpperCase()) ? Role.valueOf("ROLE_"+obj.getRole().toUpperCase()) : user.getRole());
        return userRepo.save(user);
    }

    public User createSuperAdmin(UserDto.Request.CreateAdmin obj) {
        User user = new User();
        user.setUsername(obj.getUsername());
        user.setEmail(obj.getEmail());
        user.setPassword(passwordEncoder.encode(obj.getPassword()));
        user.setRole(Role.ROLE_SUPER_ADMIN);
        user.setCreatedAt(new Date());
        return userRepo.save(user);
    }

}
