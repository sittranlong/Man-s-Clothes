package io.github.pudo58.endpoint.v2;

import io.github.pudo58.base.entity.Review;
import io.github.pudo58.base.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v2/review")
@RequiredArgsConstructor
public class ReviewV2Controller {
    private final ReviewService reviewService;

    @PostMapping("/create")
    public ResponseEntity<?> createReview(@RequestBody Review review) {
        return reviewService.createReview(review);
    }

    @GetMapping("/findByProductId")
    public ResponseEntity<?> findByProductId(@RequestParam UUID productId) {
        return reviewService.findByProductId(productId);
    }
}
