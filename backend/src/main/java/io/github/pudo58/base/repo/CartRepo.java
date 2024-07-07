package io.github.pudo58.base.repo;

import io.github.pudo58.base.entity.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartRepo extends JpaRepository<Cart, UUID> {
    Page<Cart> findByUserId(UUID userId, Pageable pageable);

    List<Cart> findByUserId(UUID userId);
    Cart findByUserIdAndProductDetailId(UUID userId, UUID productDetailId);

    List<Cart> findByUserIdAndIdIn(UUID userId, List<UUID> idList);
}
