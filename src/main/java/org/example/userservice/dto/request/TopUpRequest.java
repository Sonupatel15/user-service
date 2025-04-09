package org.example.userservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TopUpRequest {
    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.0", inclusive = false,
            message = "Amount must be greater than 0")
    private BigDecimal amount;
}