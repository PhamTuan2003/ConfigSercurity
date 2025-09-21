package com.fpt.ezpark.vn.controller;


import com.fpt.ezpark.vn.common.utill.validation.EmailExistsException;
import com.fpt.ezpark.vn.model.DTO.request.UserRequestDTO;
import com.fpt.ezpark.vn.repository.impl.UserService;
import com.fpt.ezpark.vn.repository.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final JwtService jwtService;

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String jwt = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestDTO user) {
        try {
            userService.register(user);
            return ResponseEntity.ok("User registered successfully!");
        } catch (EmailExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
