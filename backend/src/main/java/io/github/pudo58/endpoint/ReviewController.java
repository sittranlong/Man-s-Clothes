package io.github.pudo58.endpoint;

import io.github.pudo58.base.entity.Review;
import io.github.pudo58.base.service.ReviewService;
import io.github.pudo58.dto.CommonRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/findBySearch")
    public Page<Review> findBySearch(@RequestBody CommonRequest model) {
        return reviewService.findBySearch(model);
    }

    @PutMapping("/approve/{reviewId}")
    public void approveReview(@PathVariable UUID reviewId) {
        reviewService.approveReview(reviewId);
    }
}
