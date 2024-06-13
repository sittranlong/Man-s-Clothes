package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.Wishlist;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface WishListService {
    ResponseEntity<?> add(UUID productId);

    void remove(UUID id);

    List<Wishlist> findAll();

    void removeMultiple(List<UUID> productIds);
}
