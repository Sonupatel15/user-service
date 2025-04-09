package org.example.userservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BuyMetroCardRequest {
    @NotBlank(message = "User ID cannot be blank")
    private String userId;

    @NotNull(message = "Initial balance cannot be null")
    @DecimalMin(value = "0.0", inclusive = false,
            message = "Initial balance must be greater than 0")
    private BigDecimal initialBalance;
}