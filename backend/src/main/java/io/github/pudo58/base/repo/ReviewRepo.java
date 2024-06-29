package io.github.pudo58.base.repo;

import io.github.pudo58.base.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepo extends JpaRepository<Review, UUID> {
    List<Review> findByProductId(UUID productId);
}
