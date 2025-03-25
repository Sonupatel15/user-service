package org.example.userservice.repository;

import org.example.userservice.model.MetroCard;
import org.example.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MetroCardRepository extends JpaRepository<MetroCard, UUID> {

    // Check if a user has an active metro card
    boolean existsByUserAndIsActive(User user, boolean isActive);

    // Find the active metro card for a user (if any)
    Optional<MetroCard> findByUserAndIsActive(User user, boolean isActive);
}