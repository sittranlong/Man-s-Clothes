package io.github.pudo58.base.repo;

import io.github.pudo58.base.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartRepo extends JpaRepository<Cart, UUID> {
    List<Cart> findByUserId(UUID userId);
}
