package com.security.jwt.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest) {
        User user = new User(
                UUID.randomUUID(),
                userRequest.getEmail(),
                userRequest.getPassword(),
                Role.USER
        );
        User createdUser = userService.createUser(user);
        if (createdUser != null) {
            return ResponseEntity.ok(createdUser.toResponse());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot create user.");
        }
    }

    @GetMapping
    public List<UserResponse> listAll() {
        return userService.findAll().stream()
                .map(User::toResponse)
                .toList();
    }

    @GetMapping("/{uuid}")
    public UserResponse findByUUID(@PathVariable UUID uuid) {
        User user = userService.findByUUID(uuid);
        if (user != null) {
            return user.toResponse();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteByUUID(@PathVariable UUID uuid) {
        boolean success = userService.deleteByUUID(uuid);
        if (success) {
            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
    }
}

