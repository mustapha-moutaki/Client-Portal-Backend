package org.mustapha.ClientPortal.config;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mustapha.ClientPortal.model.User;
import org.mustapha.ClientPortal.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpSession session) {
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());

        if (userOpt.isPresent() && userOpt.get().getPassword().equals(request.getPassword())) {
            User user = userOpt.get();

            // Store user ID in session
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());

            LoginResponse response = new LoginResponse(user.getId(), user.getUsername(), "Login successful");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(new MessageResponse("Invalid username or password"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(new MessageResponse("Logout successful"));
    }

    // DTOs
    @Data @NoArgsConstructor @AllArgsConstructor
    static class LoginRequest {
        private String username;
        private String password;
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    static class LoginResponse {
        private Long id;
        private String username;
        private String message;
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    static class MessageResponse {
        private String message;
    }
}
