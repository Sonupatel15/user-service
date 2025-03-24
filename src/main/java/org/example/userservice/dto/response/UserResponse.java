package org.example.userservice.dto.response;

import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class UserResponse {

    private UUID userId;
    private String name;
    private String email;
    private String mobile;
    private Boolean isActive;
    private String address;
    private LocalDate dob;
}