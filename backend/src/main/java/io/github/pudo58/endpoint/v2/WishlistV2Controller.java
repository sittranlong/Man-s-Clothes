package io.github.pudo58.endpoint.v2;

import io.github.pudo58.base.entity.Wishlist;
import io.github.pudo58.base.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v2/wishlist")
@RequiredArgsConstructor
public class WishlistV2Controller {
    private final WishListService wishListService;

    @PostMapping("/add/{productId}")
    public ResponseEntity<?> add(@PathVariable UUID productId) {
        return wishListService.add(productId);
    }

    @DeleteMapping("/remove/{id}")
    public void remove(@PathVariable UUID id) {
        wishListService.remove(id);
    }

    @GetMapping("/findAll")
    public List<Wishlist> findAll() {
        return wishListService.findAll();
    }

    @PostMapping("/removeMultiple")
    public void remove(@RequestParam List<UUID> ids) {
        wishListService.removeMultiple(ids);
    }

}
