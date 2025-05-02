package org.example.dto.request;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
    private String userName;
    private String role;
}
