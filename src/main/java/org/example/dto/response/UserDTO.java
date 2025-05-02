package org.example.dto.response;

import lombok.Builder;

@Builder
public class UserDTO {
    private String userName;
    private String userEmail;
    private String role;

}
