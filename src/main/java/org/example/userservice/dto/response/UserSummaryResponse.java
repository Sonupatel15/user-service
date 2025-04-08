package org.example.userservice.dto.response;

import lombok.Data;
import java.util.UUID;

@Data
public class UserSummaryResponse {
    private UUID userId;
    private String name;
    private String email;
    private Boolean isActive;
}