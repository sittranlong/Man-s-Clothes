package io.github.pudo58.base.repo;

import io.github.pudo58.base.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface WishListRepo extends JpaRepository<Wishlist, UUID> {
    List<Wishlist> findAllByUserId(UUID userId);

    List<Wishlist> findByUserIdAndProductId(UUID userId, UUID productId);
}
