package io.github.pudo58.endpoint.v2;

import io.github.pudo58.base.entity.Cart;
import io.github.pudo58.base.service.CartService;
import io.github.pudo58.dto.CartRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v2/cart")
@RequiredArgsConstructor
public class CartV2Controller {
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody CartRequest model) {
        return this.cartService.add(model);
    }

    @PostMapping("/get")
    public Page<Cart> get(@RequestBody CartRequest model) {
        return this.cartService.get(model);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable UUID id) {
        this.cartService.delete(id);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody CartRequest model) {
        return this.cartService.update(model);
    }
}
