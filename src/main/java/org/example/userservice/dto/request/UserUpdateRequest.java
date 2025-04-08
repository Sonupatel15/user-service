package org.example.userservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserUpdateRequest extends BaseUserRequest {
    @NotNull(message = "Active status cannot be null")
    private Boolean isActive;

    @Size(min = 8, max = 20, message = "Password must be 8-20 characters")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$",
            message = "Password must contain at least one digit, one lowercase and one uppercase letter"
    )
    private String password;

    private String confirmPassword;
}