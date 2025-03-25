package org.example.userservice.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class BuyMetroCardRequest {

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotNull(message = "Initial balance is required")
    private BigDecimal initialBalance;
}