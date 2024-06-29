package io.github.pudo58.base.service;

import io.github.pudo58.base.repo.CartRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepo cartRepo;

    @Override
    public ResponseEntity<?> add(UUID productId) {
        return null;
    }
}
