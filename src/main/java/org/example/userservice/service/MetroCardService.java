package org.example.userservice.service;

import org.example.userservice.dto.request.BuyMetroCardRequest;
import org.example.userservice.dto.response.MetroCardResponse;
import org.example.userservice.exception.ActiveMetroCardExistsException;
import org.example.userservice.exception.MetroCardNotFoundException;
import org.example.userservice.exception.UserNotFoundException;
import org.example.userservice.model.MetroCard;
import org.example.userservice.model.User;
import org.example.userservice.repository.MetroCardRepository;
import org.example.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class MetroCardService {

    @Autowired
    private MetroCardRepository metroCardRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public MetroCardResponse buyMetroCard(BuyMetroCardRequest request) {
        // Fetch user from DB
        User user = userRepository.findById(UUID.fromString(request.getUserId()))
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + request.getUserId()));

        // Check if the user already has an active metro card
        if (metroCardRepository.existsByUserAndIsActive(user, true)) {
            throw new ActiveMetroCardExistsException("User already has an active metro card. Please deactivate it before purchasing a new one.");
        }

        // Create and save a new metro card
        MetroCard metroCard = new MetroCard();
        metroCard.setUser(user);
        metroCard.setBalance(request.getInitialBalance());
        metroCard.setActive(true);

        metroCard = metroCardRepository.save(metroCard);

        // Return response
        return new MetroCardResponse(metroCard.getCardId().toString(), metroCard.getBalance(), metroCard.isActive());
    }

    public MetroCardResponse getMetroCard(String cardId) {
        MetroCard metroCard = metroCardRepository.findById(UUID.fromString(cardId))
                .orElseThrow(() -> new MetroCardNotFoundException("Metro card not found with id: " + cardId));

        return new MetroCardResponse(metroCard.getCardId().toString(), metroCard.getBalance(), metroCard.isActive());
    }

    public MetroCardResponse getMetroCardByUser(String userId) {
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        MetroCard metroCard = metroCardRepository.findByUserAndIsActive(user, true)
                .orElseThrow(() -> new MetroCardNotFoundException("No active metro card found for user id: " + userId));

        return new MetroCardResponse(metroCard.getCardId().toString(), metroCard.getBalance(), metroCard.isActive());
    }

    @Transactional
    public String deactivateMetroCard(String cardId) {
        MetroCard metroCard = metroCardRepository.findById(UUID.fromString(cardId))
                .orElseThrow(() -> new MetroCardNotFoundException("Metro card not found with id: " + cardId));

        metroCard.setActive(false);
        metroCardRepository.save(metroCard);
        return "MetroCard successfully deactivated : " + cardId;
    }
}