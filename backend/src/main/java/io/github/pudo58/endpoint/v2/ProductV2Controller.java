package io.github.pudo58.endpoint.v2;


import io.github.pudo58.base.entity.Product;
import io.github.pudo58.base.service.ProductService;
import io.github.pudo58.dto.ProductCard;
import io.github.pudo58.dto.ProductSearchRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v2/product")
@RequiredArgsConstructor
public class ProductV2Controller {
    private final ProductService productService;

    @PostMapping("/findByFilter")
    public Page<ProductCard> findByFilter(@RequestBody ProductSearchRequest model) {
        return productService.findByFilter(model);
    }

    @GetMapping("/get/{productId}")
    public Product getProduct(@PathVariable UUID productId) {
        return productService.findById(productId);
    }
}
