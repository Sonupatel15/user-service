package org.example.userservice.service;

import jakarta.transaction.Transactional;
import org.example.userservice.dto.request.*;
import org.example.userservice.dto.response.UserResponse;
import org.example.userservice.dto.response.UserSummaryResponse;
import org.example.userservice.exception.BadRequestException;
import org.example.userservice.exception.UserNotFoundException;
import org.example.userservice.model.User;
import org.example.userservice.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(UserCreateRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match.");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists.");
        }

        if (userRepository.existsByMobile(request.getMobile())) {
            throw new BadRequestException("Mobile number already exists.");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .mobile(request.getMobile())
                .password(request.getPassword())
                .isActive(true)
                .address(request.getAddress())
                .dob(request.getDob())
                .build();

        userRepository.save(user);
        return mapToUserResponse(user);
    }

    public UserResponse getUser(UUID userId) {
        User user = getUserOrThrow(userId);
        return mapToUserResponse(user);
    }

    public Page<UserSummaryResponse> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::mapToUserSummary);
    }

    public UserResponse updateUser(UUID userId, UserUpdateRequest request) {
        User user = getUserOrThrow(userId);

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setMobile(request.getMobile());
        user.setAddress(request.getAddress());
        user.setDob(request.getDob());
        user.setIsActive(request.getIsActive());

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            if (!request.getPassword().equals(request.getConfirmPassword())) {
                throw new BadRequestException("Passwords do not match.");
            }
            user.setPassword(request.getPassword());
        }

        return mapToUserResponse(userRepository.save(user));
    }

    public void deleteUser(UUID userId) {
        User user = getUserOrThrow(userId);
        user.setIsActive(false);
        userRepository.save(user);
    }

    public void changePassword(UUID userId, PasswordResetRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match.");
        }

        User user = getUserOrThrow(userId);
        user.setPassword(request.getNewPassword());
        userRepository.save(user);
    }

    public void updateStatus(UUID userId, boolean active) {
        User user = getUserOrThrow(userId);
        user.setIsActive(active);
        userRepository.save(user);
    }

    public UserResponse patchUser(UUID userId, Map<String, Object> updates) {
        User user = getUserOrThrow(userId);

        if (updates.containsKey("name")) {
            user.setName((String) updates.get("name"));
        }

        if (updates.containsKey("email")) {
            String email = (String) updates.get("email");
            if (!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
                throw new BadRequestException("Invalid email format.");
            }
            user.setEmail(email);
        }

        if (updates.containsKey("mobile")) {
            String mobile = (String) updates.get("mobile");
            if (!mobile.matches("^\\d{10}$")) {
                throw new BadRequestException("Mobile number must be 10 digits.");
            }
            user.setMobile(mobile);
        }

        if (updates.containsKey("address")) {
            user.setAddress((String) updates.get("address"));
        }

        if (updates.containsKey("dob")) {
            try {
                user.setDob(LocalDate.parse((String) updates.get("dob")));
            } catch (Exception e) {
                throw new BadRequestException("Invalid date format for dob. Use yyyy-MM-dd.");
            }
        }

        if (updates.containsKey("isActive")) {
            user.setIsActive(Boolean.parseBoolean(updates.get("isActive").toString()));
        }

        return mapToUserResponse(userRepository.save(user));
    }

    private User getUserOrThrow(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setUserId(user.getUserId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setMobile(user.getMobile());
        response.setIsActive(user.getIsActive());
        response.setAddress(user.getAddress());
        response.setDob(user.getDob());
        return response;
    }

    private UserSummaryResponse mapToUserSummary(User user) {
        UserSummaryResponse summary = new UserSummaryResponse();
        summary.setUserId(user.getUserId());
        summary.setName(user.getName());
        summary.setEmail(user.getEmail());
        summary.setIsActive(user.getIsActive());
        return summary;
    }
}
