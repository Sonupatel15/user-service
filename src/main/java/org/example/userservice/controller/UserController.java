package org.example.userservice.controller;

import jakarta.validation.Valid;
import org.example.userservice.dto.request.*;
import org.example.userservice.dto.response.UserResponse;
import org.example.userservice.dto.response.UserSummaryResponse;
import org.example.userservice.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@Valid @RequestBody UserCreateRequest request) {
        return userService.createUser(request);
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable UUID userId) {
        return userService.getUser(userId);
    }

    @GetMapping
    public Page<UserSummaryResponse> getUsers(Pageable pageable) {
        return userService.getUsers(pageable);
    }

    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable UUID userId,
                                   @Valid @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
    }

    @PostMapping("/{userId}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable UUID userId,
                               @Valid @RequestBody PasswordResetRequest request) {
        userService.changePassword(userId, request);
    }

    @PatchMapping("/{userId}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable UUID userId,
                             @RequestParam boolean active) {
        userService.updateStatus(userId, active);
    }

    @PatchMapping("/{userId}")
    public UserResponse patchUser(@PathVariable UUID userId,
                                  @RequestBody Map<String, Object> updates) {
        return userService.patchUser(userId, updates);
    }

}