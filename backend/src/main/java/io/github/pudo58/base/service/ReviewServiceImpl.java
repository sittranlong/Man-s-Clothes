package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.Review;
import io.github.pudo58.base.entity.User;
import io.github.pudo58.base.repo.ProductRepo;
import io.github.pudo58.base.repo.ReviewRepo;
import io.github.pudo58.constant.ReviewConst;
import io.github.pudo58.dto.CommonRequest;
import io.github.pudo58.record.AlertResponseRecord;
import io.github.pudo58.util.ImageBase64;
import io.github.pudo58.util.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepo reviewRepo;
    private final Message message;
    private final ProductRepo productRepo;

    @Override
    public ResponseEntity<?> createReview(Review review) {
        boolean isBuy = reviewRepo.isBuy(review.getProductId(), User.getContext().getId());
        if (isBuy) {
            if (review.getImageBase64() != null) {
                review.setImage(ImageBase64.setImageBase64(review.getImageBase64()));
            }
            review.setUser(User.getContext());
            review.setStatus(ReviewConst.STATUS_PENDING);
            review.setProduct(productRepo.findById(review.getProductId()).orElse(null));
            reviewRepo.save(review);
            return ResponseEntity.ok().body(new AlertResponseRecord(message.getMessage("review.created"), HttpStatus.OK.value()));
        } else {
            return ResponseEntity.badRequest().body(new AlertResponseRecord(message.getMessage("review.not.buy"), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @Override
    public ResponseEntity<?> findByProductId(UUID productId) {
        return ResponseEntity.ok().body(reviewRepo.findByProductId(productId));
    }

    @Override
    public Page<Review> findBySearch(CommonRequest model) {
        return reviewRepo.findBySearch(model, model.getPageable());
    }

    @Override
    public void approveReview(UUID reviewId) {
        Review review = reviewRepo.findById(reviewId).orElse(null);
        if (review != null) {
            review.setStatus(ReviewConst.STATUS_APPROVED);
            reviewRepo.save(review);
        }
    }
}
