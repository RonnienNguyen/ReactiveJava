package com.learnreactive.reactive.test;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {

    @Test
    public void fluxTest() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
//                        .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                        .log();
        stringFlux
                .subscribe(System.out::println,
                        (e) -> System.err.println("Exception is " + e),
                        () -> System.out.println("Completed"));

    }

    @Test
    public void fluxTestElements_WithoutError() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Spring Flux")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();
        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                .expectErrorMessage("Exception Occurred")
                .verify();
    }

    @Test
    public void fluxTestElements_WithoutError1() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Spring Flux")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Spring", "Spring Boot", "Spring Flux")
                .expectErrorMessage("Exception Occurred")
                .verify();
    }


    @Test
    public void monoTest() {
        Mono<String> stringMono = Mono.just("Spring");
        StepVerifier.create(stringMono.log())
                .expectNext("Spring")
                .verifyComplete();
    }

    @Test
    public void monoTest_Error() {
        StepVerifier.create(Mono.error(new RuntimeException("Exception Occurred")).log())
                .expectError(RuntimeException.class)
                .verify();
    }
}
