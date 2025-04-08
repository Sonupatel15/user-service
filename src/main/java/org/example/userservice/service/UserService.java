package org.example.userservice.service;

import org.example.userservice.dto.request.UserRequest;
import org.example.userservice.dto.response.UserResponse;
import org.example.userservice.exception.TravelIdNotFoundException;
import org.example.userservice.exception.UserNotFoundException;
import org.example.userservice.model.TravelHistory;
import org.example.userservice.model.User;
import org.example.userservice.repository.TravelHistoryRepository;
import org.example.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        if (userRepository.existsByMobile(userRequest.getMobile())) {
            throw new RuntimeException("Mobile number already exists");
        }

        // Manually mapping DTO to Entity
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setMobile(userRequest.getMobile());
        user.setPassword(userRequest.getPassword());
        user.setIsActive(userRequest.getIsActive());
        user.setAddress(userRequest.getAddress());
        user.setDob(userRequest.getDob());

        user = userRepository.save(user);

        // Manually mapping Entity to DTO
        return mapToUserResponse(user);
    }

    public UserResponse getUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        return mapToUserResponse(user);
    }

    @Transactional
    public UserResponse updateUser(UUID userId, UserRequest userRequest) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        if (!existingUser.getEmail().equals(userRequest.getEmail()) &&
                userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (!existingUser.getMobile().equals(userRequest.getMobile()) &&
                userRepository.existsByMobile(userRequest.getMobile())) {
            throw new RuntimeException("Mobile number already exists");
        }

        // Manually updating fields
        existingUser.setName(userRequest.getName());
        existingUser.setEmail(userRequest.getEmail());
        existingUser.setMobile(userRequest.getMobile());
        existingUser.setPassword(userRequest.getPassword());
        existingUser.setIsActive(userRequest.getIsActive());
        existingUser.setAddress(userRequest.getAddress());
        existingUser.setDob(userRequest.getDob());

        existingUser = userRepository.save(existingUser);

        return mapToUserResponse(existingUser);
    }

    @Transactional
    public void deleteUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        userRepository.delete(user);
    }

    // Helper method to convert Entity to Response DTO
    private UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getUserId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setMobile(user.getMobile());
        userResponse.setIsActive(user.getIsActive());
        userResponse.setAddress(user.getAddress());
        userResponse.setDob(user.getDob());
        return userResponse;
    }

}