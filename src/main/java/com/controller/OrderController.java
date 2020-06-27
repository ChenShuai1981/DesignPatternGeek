package com.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @PostMapping
    public Order createOrder(String id, String name) {
        return new Order(id, name);
    }

}
