package org.example.userservice.controller;

import org.example.userservice.dto.request.BuyMetroCardRequest;
import org.example.userservice.dto.response.MetroCardResponse;
import org.example.userservice.service.MetroCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/metro-cards")
public class MetroCardController {

    @Autowired
    private MetroCardService metroCardService;

    /**
     * Buy a new metro card for a user
     */
    @PostMapping("/buy")
    public ResponseEntity<MetroCardResponse> buyMetroCard(@RequestBody BuyMetroCardRequest request) {
        MetroCardResponse response = metroCardService.buyMetroCard(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Get metro card details by card ID
     */
    @GetMapping("/{cardId}")
    public ResponseEntity<MetroCardResponse> getMetroCard(@PathVariable UUID cardId) {
        MetroCardResponse response = metroCardService.getMetroCard(cardId.toString());
        return ResponseEntity.ok(response);
    }

    /**
     * Get the active metro card of a user
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<MetroCardResponse> getMetroCardByUser(@PathVariable UUID userId) {
        MetroCardResponse response = metroCardService.getMetroCardByUser(userId.toString());
        return ResponseEntity.ok(response);
    }

    /**
     * Deactivate a metro card by card ID
     */
    @DeleteMapping("/deactivate/{cardId}")
    public ResponseEntity<String> deactivateMetroCard(@PathVariable UUID cardId) {
        String message = metroCardService.deactivateMetroCard(cardId.toString());
        return ResponseEntity.ok(message); // Returns a success message instead of 204 No Content
    }
}