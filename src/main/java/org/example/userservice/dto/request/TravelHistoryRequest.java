package org.example.userservice.dto.request;

import lombok.*;
import org.example.userservice.enums.TravelStatus;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelHistoryRequest {
    private UUID transactionId;
    private UUID userId;
    private int fromStation;
    private int toStation;
    private BigDecimal distance;
    private BigDecimal baseFare;
    private BigDecimal penalty;
    private BigDecimal totalFare;
    private TravelStatus status;
}