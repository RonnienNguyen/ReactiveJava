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
@RequestMapping("/api/items")
public class ReactiveController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/")
    public Flux<String> getAllItems() {
        return Flux.just("Item 1", "Item 2", "Item 3");
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<String>> getItemById (@PathVariable String id) {
        return Mono.just("Item" + id)
                .map(item -> ResponseEntity.ok(item))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/getAll")
    public Flux<ResponseEntity<Item>> getAll() {
        return itemService.getAllItems()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build()); // Handle empty case if needed
    }

    @PostMapping("/")
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
