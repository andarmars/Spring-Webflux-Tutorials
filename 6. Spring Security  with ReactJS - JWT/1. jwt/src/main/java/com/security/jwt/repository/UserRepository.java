package com.security.jwt.repository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public class UserRepository {

    private final PasswordEncoder encoder;
    private final Set<User> users = new HashSet<>();

    public UserRepository(PasswordEncoder encoder) {
        this.encoder = encoder;
        initializeUsers();
    }

    private void initializeUsers() {
        users.add(new User(UUID.randomUUID(), "email-1@gmail.com", encoder.encode("pass1"), Role.USER));
        users.add(new User(UUID.randomUUID(), "email-2@gmail.com", encoder.encode("pass2"), Role.ADMIN));
        users.add(new User(UUID.randomUUID(), "email-3@gmail.com", encoder.encode("pass3"), Role.USER));
    }

    public boolean save(User user) {
        User updated = new User(user.getId(), user.getEmail(), encoder.encode(user.getPassword()), user.getRole());
        return users.add(updated);
    }

    public User findByEmail(String email) {
        Optional<User> userOptional = users.stream().filter(u -> u.getEmail().equals(email)).findFirst();
        return userOptional.orElse(null);
    }

    public Set<User> findAll() {
        return users;
    }

    public User findByUUID(UUID uuid) {
        Optional<User> userOptional = users.stream().filter(u -> u.getId().equals(uuid)).findFirst();
        return userOptional.orElse(null);
    }

    public boolean deleteByUUID(UUID uuid) {
        User foundUser = findByUUID(uuid);
        return foundUser != null && users.removeIf(u -> u.getId().equals(uuid));
    }
}

