package org.example.userservice.service;

import org.example.userservice.dto.request.BuyMetroCardRequest;
import org.example.userservice.dto.request.TopUpRequest;
import org.example.userservice.dto.response.MetroCardResponse;
import org.example.userservice.exception.*;
import org.example.userservice.model.MetroCard;
import org.example.userservice.model.User;
import org.example.userservice.repository.MetroCardRepository;
import org.example.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class MetroCardService {

    private final MetroCardRepository metroCardRepository;
    private final UserRepository userRepository;

    public MetroCardService(MetroCardRepository metroCardRepository,
                            UserRepository userRepository) {
        this.metroCardRepository = metroCardRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public MetroCardResponse purchaseMetroCard(BuyMetroCardRequest request) {
        validatePurchaseRequest(request);

        User user = getUserById(UUID.fromString(request.getUserId()));
        validateNoActiveCard(user);

        MetroCard metroCard = createNewMetroCard(user, request.getInitialBalance());
        return mapToResponse(metroCard);
    }

    public MetroCardResponse getMetroCard(UUID cardId) {
        MetroCard metroCard = metroCardRepository.findById(cardId)
                .orElseThrow(() -> new MetroCardNotFoundException(
                        "Metro card not found with id: " + cardId));
        return mapToResponse(metroCard);
    }

    public MetroCardResponse getActiveUserMetroCard(UUID userId) {
        User user = getUserById(userId);
        MetroCard metroCard = metroCardRepository.findByUserAndIsActive(user, true)
                .orElseThrow(() -> new MetroCardNotFoundException(
                        "No active metro card found for user id: " + userId));
        return mapToResponse(metroCard);
    }

    @Transactional
    public void deactivateMetroCard(UUID cardId) {
        MetroCard metroCard = getActiveMetroCard(cardId);
        metroCard.setActive(false);
        metroCardRepository.save(metroCard);
    }

    @Transactional
    public MetroCardResponse topUpMetroCard(UUID cardId, TopUpRequest request) {
        validateTopUpAmount(request.getAmount());

        MetroCard metroCard = getActiveMetroCard(cardId);
        metroCard.setBalance(metroCard.getBalance().add(request.getAmount()));
        metroCard = metroCardRepository.save(metroCard);
        return mapToResponse(metroCard);
    }

    @Transactional
    public MetroCardResponse deductFare(UUID cardId, BigDecimal amount) {
        validateDeductionAmount(amount);

        MetroCard metroCard = getActiveMetroCard(cardId);
        validateSufficientBalance(metroCard, amount);

        metroCard.setBalance(metroCard.getBalance().subtract(amount));
        metroCard = metroCardRepository.save(metroCard);
        return mapToResponse(metroCard);
    }

    // ========== Private Helper Methods ==========

    private User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found with id: " + userId));
    }

    private MetroCard getActiveMetroCard(UUID cardId) {
        MetroCard metroCard = metroCardRepository.findById(cardId)
                .orElseThrow(() -> new MetroCardNotFoundException(
                        "Metro card not found with id: " + cardId));

        if (!metroCard.isActive()) {
            throw new BadRequestException(
                    "Cannot perform operation on inactive metro card: " + cardId);
        }

        return metroCard;
    }

    private void validatePurchaseRequest(BuyMetroCardRequest request) {
        if (request.getInitialBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException(
                    "Initial balance cannot be negative");
        }
    }

    private void validateNoActiveCard(User user) {
        if (metroCardRepository.existsByUserAndIsActive(user, true)) {
            throw new ActiveMetroCardExistsException(
                    "User already has an active metro card");
        }
    }

    private void validateTopUpAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException(
                    "Top-up amount must be positive");
        }
    }

    private void validateDeductionAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException(
                    "Deduction amount must be positive");
        }
    }

    private void validateSufficientBalance(MetroCard metroCard, BigDecimal amount) {
        if (metroCard.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException(
                    String.format("Insufficient balance. Current: %.2f, Required: %.2f",
                            metroCard.getBalance(), amount));
        }
    }

    private MetroCard createNewMetroCard(User user, BigDecimal initialBalance) {
        return metroCardRepository.save(
                MetroCard.builder()
                        .user(user)
                        .balance(initialBalance)
                        .isActive(true)
                        .build());
    }

    private MetroCardResponse mapToResponse(MetroCard metroCard) {
        return MetroCardResponse.builder()
                .cardId(metroCard.getCardId().toString())
                .balance(metroCard.getBalance())
                .isActive(metroCard.isActive())
                .build();
    }
}