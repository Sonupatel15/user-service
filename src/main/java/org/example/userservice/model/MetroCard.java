package org.example.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@Table(name = "metro_cards")
@AllArgsConstructor
public class MetroCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID cardId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private boolean isActive = true;

    public MetroCard() {}
}