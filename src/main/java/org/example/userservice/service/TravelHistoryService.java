package org.example.userservice.service;

import org.example.userservice.dto.request.TravelHistoryRequest;
import org.example.userservice.enums.TravelStatus;
import org.example.userservice.exception.TravelIdNotFoundException;
import org.example.userservice.model.TravelHistory;
import org.example.userservice.repository.TravelHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TravelHistoryService {

    private final TravelHistoryRepository repository;

    @Autowired
    private TravelHistoryRepository travelHistoryRepository;

    public TravelHistory createHistory(TravelHistoryRequest request) {
        TravelHistory travelHistory = TravelHistory.builder()
                .transactionId(request.getTransactionId())
                .userId(request.getUserId())
                .fromStation(request.getFromStation())
                .toStation(request.getToStation())
                .distance(request.getDistance())
                .baseFare(request.getBaseFare())
                .penalty(request.getPenalty() != null ? request.getPenalty() : BigDecimal.ZERO)
                .totalFare(request.getBaseFare().add(request.getPenalty() != null ? request.getPenalty() : BigDecimal.ZERO))
                .status(request.getStatus() != null ? request.getStatus() : TravelStatus.NOT_STARTED)
                .build();

        return repository.save(travelHistory);
    }

    public void updateStatus(UUID travelId, TravelStatus status) {
        TravelHistory history = repository.findByTravelId(travelId)
                .orElseThrow(() -> new TravelIdNotFoundException("Travel ID not found"));
        history.setStatus(status);
        repository.save(history);
    }

    public boolean doesTravelExist(UUID travelId) {
        return repository.existsByTravelId(travelId);
    }

    public void updatePenalty(UUID travelId, BigDecimal penaltyAmount) {
        TravelHistory history = travelHistoryRepository.findByTravelId(travelId)
                .orElseThrow(() -> new TravelIdNotFoundException("Travel history not found"));

        history.setPenalty(penaltyAmount);
        history.setTotalFare(history.getBaseFare().add(penaltyAmount));
        travelHistoryRepository.save(history);
    }

    public BigDecimal getBaseFare(UUID travelId) {
        TravelHistory history = repository.findByTravelId(travelId)
                .orElseThrow(() -> new TravelIdNotFoundException("Travel ID not found"));
        return history.getBaseFare();
    }
}