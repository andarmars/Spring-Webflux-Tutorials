package com.kapelles.tutorial.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.kapelles.tutorial.model.UserEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository<UserEntity, Long> {
    
    Flux<UserEntity> findByFirstnameContaining(String name);
    
    @SuppressWarnings("null")
    Mono<UserEntity> findById(Long id);
}
