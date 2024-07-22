package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.Review;
import io.github.pudo58.dto.CommonRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface ReviewService {
    ResponseEntity<?> createReview(Review review);

    ResponseEntity<?> findByProductId(UUID productId);

    Page<Review> findBySearch(CommonRequest model);

    void approveReview(UUID reviewId);
}
