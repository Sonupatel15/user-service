package org.example.userservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PasswordResetRequest {
    @NotBlank(message = "New password cannot be blank")
    @Size(min = 8, max = 20, message = "Password must be 8-20 characters")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$",
            message = "Password must contain at least one digit, one lowercase and one uppercase letter"
    )
    private String newPassword;

    @NotBlank(message = "Confirm password cannot be blank")
    private String confirmPassword;
}