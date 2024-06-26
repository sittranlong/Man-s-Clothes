package io.github.pudo58.endpoint;

import io.github.pudo58.base.controller.AbstractController;
import io.github.pudo58.base.entity.Product;
import io.github.pudo58.base.service.ProductService;
import io.github.pudo58.dto.ProductSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController extends AbstractController<Product> {
    private final ProductService productService;

    @PostMapping("/findBySearch")
    public Page<Product> findBySearch(@RequestBody ProductSearchRequest model) {
        return productService.findBySearch(model);
    }
}
