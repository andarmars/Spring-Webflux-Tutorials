package com.security.jwt.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        User found = userRepository.findByEmail(user.getEmail());

        if (found == null) {
            userRepository.save(user);
            return user;
        }
        return null;
    }

    public User findByUUID(UUID uuid) {
        return userRepository.findByUUID(uuid);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean deleteByUUID(UUID uuid) {
        return userRepository.deleteByUUID(uuid);
    }
}
