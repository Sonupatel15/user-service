package org.example.userservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.userservice.enums.TravelStatus;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "travel_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "travel_id", updatable = false, nullable = false)
    private UUID travelId;

    @Column(name = "transaction_id", nullable = false)
    private UUID transactionId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "from_station", nullable = false)
    private int fromStation;

    @Column(name = "to_station", nullable = false)
    private int toStation;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal distance;

    @Column(name = "base_fare", nullable = false, precision = 10, scale = 2)
    private BigDecimal baseFare;

    @Column(name = "penalty", nullable = false, precision = 10, scale = 2)
    private BigDecimal penalty;

    @Column(name = "total_fare", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalFare;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TravelStatus status;

    @PrePersist
    public void prePersist() {
        if (penalty == null) {
            penalty = BigDecimal.ZERO;
        }
        if (totalFare == null && baseFare != null) {
            totalFare = baseFare.add(penalty);
        }
        if (status == null) {
            status = TravelStatus.NOT_STARTED;
        }
    }
}