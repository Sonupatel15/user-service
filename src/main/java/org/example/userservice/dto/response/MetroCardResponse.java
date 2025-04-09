package org.example.userservice.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetroCardResponse {
    private String cardId;
    private BigDecimal balance;
    private boolean isActive;
}