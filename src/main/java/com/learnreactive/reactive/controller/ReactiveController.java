package com.learnreactive.reactive.controller;


import com.learnreactive.reactive.model.Item;
import com.learnreactive.reactive.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ReactiveController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/items")
    public Flux<String> getAllItems() {
        return Flux.just("Item 1", "Item 2", "Item 3");
    }

    @GetMapping("/items/{id}")
    public Mono<ResponseEntity<String>> getItemById (@PathVariable String id) {
        return Mono.just("Item" + id)
                .map(item -> ResponseEntity.ok(item))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/items/getAll")
    public Flux<ResponseEntity<Item>> getAll() {
        return itemService.getAllItems()
                .map(ResponseEntity::ok);
    }

    @PostMapping("/items")
    public Mono<ResponseEntity<Object>> createItem(@RequestBody String newItem) {
        // Create a map to hold the response data
        Map<String, Object> responseJson = new HashMap<>();
        responseJson.put("status", "success");
        responseJson.put("message", "Created " + newItem);

        // Return ResponseEntity with the responseJson map directly
        return Mono.just(responseJson)
                .map(json -> ResponseEntity.ok()
                        .body(json));
    }


}
