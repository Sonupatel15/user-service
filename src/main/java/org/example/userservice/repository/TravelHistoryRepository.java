package org.example.userservice.repository;

import org.example.userservice.model.TravelHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TravelHistoryRepository extends JpaRepository<TravelHistory, UUID> {
    Optional<TravelHistory> findByTravelId(UUID travelId);
    boolean existsByTravelId(UUID travelId);

}