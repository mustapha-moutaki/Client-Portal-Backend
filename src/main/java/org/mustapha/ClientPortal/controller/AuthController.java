package org.mustapha.ClientPortal.controller;

import lombok.RequiredArgsConstructor;
import org.mustapha.ClientPortal.dto.request.LoginRequest;
import org.mustapha.ClientPortal.dto.response.AuthResponse;
import org.mustapha.ClientPortal.repository.UserRepository;
import org.mustapha.ClientPortal.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;


    @PostMapping("/login")

    public ResponseEntity<?> login(@RequestBody LoginRequest request) {


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );


        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));


        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", user.getRole().toString());
        extraClaims.put("userId", user.getId());

        var jwtToken = jwtService.generateToken(extraClaims, user);

        return ResponseEntity.ok(new AuthResponse(jwtToken, user.getRole().toString(), user.getId()));
    }
}