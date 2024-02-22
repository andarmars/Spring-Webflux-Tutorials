package com.kapelles.tzm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TestController {
    @GetMapping("/mono")
    public Mono<String> getMonoResult() {
        return Mono.just("Result from Mono");
    }

    @GetMapping("/flux")
    public Flux<String> getFluxResult() {
        return Flux.just("Result from Flux");
    }
}
