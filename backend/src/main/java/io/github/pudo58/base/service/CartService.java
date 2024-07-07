package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.Cart;
import io.github.pudo58.dto.CartRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface CartService {
    ResponseEntity<?> add(CartRequest model);

    Page<Cart> get(CartRequest model);

    void delete(UUID id);

    ResponseEntity<?> update(CartRequest model);
}
