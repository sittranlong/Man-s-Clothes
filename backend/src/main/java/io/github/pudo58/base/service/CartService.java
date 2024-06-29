package io.github.pudo58.base.service;

import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface CartService {
    ResponseEntity<?> add(UUID productId);
}
