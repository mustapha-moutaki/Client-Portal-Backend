package org.mustapha.ClientPortal.dto.response;

@lombok.Data
@lombok.AllArgsConstructor
public class AuthResponse {
    private String token;
    private String role;
    private Long id;
}