package io.github.pudo58.endpoint;

import io.github.pudo58.base.controller.AbstractController;
import io.github.pudo58.base.entity.Product;
import io.github.pudo58.base.service.ProductService;
import io.github.pudo58.dto.ProductSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController extends AbstractController<Product> {
    private final ProductService productService;

    @PostMapping("/findBySearch")
    public Page<Product> findBySearch(@RequestBody ProductSearchRequest model) {
        return productService.findBySearch(model);
    }

    @PostMapping("/deleteDetail/{productDetailId}")
    public void deleteDetail(@PathVariable UUID productDetailId) {
        productService.deleteDetail(productDetailId);
    }
}
