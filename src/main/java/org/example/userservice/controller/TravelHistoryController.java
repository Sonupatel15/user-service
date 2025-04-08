package org.example.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.userservice.dto.request.TravelHistoryRequest;
import org.example.userservice.dto.request.UpdatePenaltyRequest;
import org.example.userservice.enums.TravelStatus;
import org.example.userservice.model.TravelHistory;
import org.example.userservice.service.TravelHistoryService;
import org.example.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/travel-history")
@RequiredArgsConstructor
public class TravelHistoryController {

    private final TravelHistoryService service;


    @PostMapping
    public ResponseEntity<TravelHistory> createHistory(@RequestBody TravelHistoryRequest request) {
        return ResponseEntity.ok(service.createHistory(request));
    }

    @PutMapping("/status/start/{travelId}")
    public ResponseEntity<Void> markTravelStarted(@PathVariable UUID travelId) {
        service.updateStatus(travelId, TravelStatus.STARTED);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/status/complete/{travelId}")
    public ResponseEntity<Void> markTravelCompleted(@PathVariable UUID travelId) {
        service.updateStatus(travelId, TravelStatus.COMPLETED);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/exists/{travelId}")
    public ResponseEntity<Boolean> doesTravelExist(@PathVariable UUID travelId) {
        boolean exists = service.doesTravelExist(travelId);
        return ResponseEntity.ok(exists);
    }

    @PostMapping("/update-penalty")
    public ResponseEntity<String> updatePenalty(@RequestBody UpdatePenaltyRequest request) {
        service.updatePenalty(request.getTravelId(), request.getPenaltyAmount());
        return ResponseEntity.ok("Penalty updated in Travel History.");
    }

    @GetMapping("/{travelId}/base-fare")
    public ResponseEntity<BigDecimal> getBaseFare(@PathVariable UUID travelId) {
        BigDecimal baseFare = service.getBaseFare(travelId);
        return ResponseEntity.ok(baseFare);
    }
}