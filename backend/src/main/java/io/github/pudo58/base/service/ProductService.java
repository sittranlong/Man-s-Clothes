package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.Product;
import io.github.pudo58.base.service.core.Service;
import io.github.pudo58.dto.ProductCard;
import io.github.pudo58.dto.ProductSearchRequest;
import org.springframework.data.domain.Page;

public interface ProductService extends Service<Product> {
    Page<Product> findBySearch(ProductSearchRequest model);

    Page<ProductCard> findByFilter(ProductSearchRequest model);
}
