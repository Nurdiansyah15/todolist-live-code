package com.nurd.todolist.servs;

import com.nurd.todolist.models.Role;
import com.nurd.todolist.models.User;
import com.nurd.todolist.repos.UserRepo;
import com.nurd.todolist.securities.JwtService;
import com.nurd.todolist.utils.dtos.AuthDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthServ {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    // register
    public AuthDto.ResponseRegisterDto register(AuthDto.RequestRegisterDto requestRegisterDto) {
        if (userRepo.findByEmail(requestRegisterDto.getEmail()).isPresent()) {
            throw new IllegalStateException("email already exists");
        }

        User user = new User();
        user.setUsername(requestRegisterDto.getUsername());
        user.setEmail(requestRegisterDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestRegisterDto.getPassword()));
        user.setCreatedAt(new Date());
        user.setRole(Role.ROLE_USER);

        System.out.println(user);
        userRepo.save(user);
        return new AuthDto.ResponseRegisterDto(user.getId(), user.getUsername(), user.getEmail());
    }

    // login
    public AuthDto.ResponseLoginDto login(AuthDto.RequestLoginDto requestLoginDto) {

        User user = userRepo.findByEmail(requestLoginDto.getEmail()).orElseThrow(() -> new IllegalStateException("invalid credentials"));

        if (!passwordEncoder.matches(requestLoginDto.getPassword(), user.getPassword())) {
            throw new IllegalStateException("invalid username or password");
        }

        String token = jwtService.generateToken(user);

        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthDto.ResponseLoginDto(token, refreshToken);
    }

    // refresh
    public AuthDto.ResponseRefreshTokenDto refreshToken(AuthDto.RequestRefreshTokenDto requestRefreshTokenDto) {

        String refreshToken = requestRefreshTokenDto.getRefreshToken();
        if (refreshToken == null) {
            throw new IllegalStateException("refresh token not found");
        }

        String email = jwtService.extractEmail(refreshToken);

        User user = this.userRepo.findByEmail(email).orElseThrow(() -> new IllegalStateException("invalid refresh token"));
        if (jwtService.validateToken(refreshToken, user)) {
            String accessToken = jwtService.generateToken(user);
            return new AuthDto.ResponseRefreshTokenDto(accessToken);
        } else {
            throw new IllegalStateException("invalid refresh token");
        }

    }

    // userAuth
    public User getUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        return userRepo.findByUsername(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
