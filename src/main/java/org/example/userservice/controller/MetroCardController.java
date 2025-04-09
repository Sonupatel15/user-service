package org.example.userservice.controller;

import jakarta.validation.Valid;
import org.example.userservice.dto.request.BuyMetroCardRequest;
import org.example.userservice.dto.request.TopUpRequest;
import org.example.userservice.dto.response.MetroCardResponse;
import org.example.userservice.service.MetroCardService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/metro-cards")
public class MetroCardController {

    private final MetroCardService metroCardService;

    public MetroCardController(MetroCardService metroCardService) {
        this.metroCardService = metroCardService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MetroCardResponse purchaseMetroCard(@Valid @RequestBody BuyMetroCardRequest request) {
        return metroCardService.purchaseMetroCard(request);
    }

    @GetMapping("/{cardId}")
    public MetroCardResponse getMetroCard(@PathVariable UUID cardId) {
        return metroCardService.getMetroCard(cardId);
    }

    @GetMapping("/users/{userId}/active")
    public MetroCardResponse getActiveUserMetroCard(@PathVariable UUID userId) {
        return metroCardService.getActiveUserMetroCard(userId);
    }

    @PatchMapping("/{cardId}/deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateMetroCard(@PathVariable UUID cardId) {
        metroCardService.deactivateMetroCard(cardId);
    }

    @PostMapping("/{cardId}/top-up")
    public MetroCardResponse topUpMetroCard(@PathVariable UUID cardId,
                                            @Valid @RequestBody TopUpRequest request) {
        return metroCardService.topUpMetroCard(cardId, request);
    }

    @PostMapping("/{cardId}/deduct")
    public MetroCardResponse deductFare(@PathVariable UUID cardId,
                                        @RequestParam BigDecimal amount) {
        return metroCardService.deductFare(cardId, amount);
    }
}