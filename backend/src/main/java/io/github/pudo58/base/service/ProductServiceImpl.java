package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.Product;
import io.github.pudo58.base.service.core.AbstractService;
import io.github.pudo58.util.ImageBase64;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl extends AbstractService<Product> implements ProductService {
    @Override
    public Product save(Product product) {
        if (product.getImageBase64() != null) {
            product.setImage(ImageBase64.setImageBase64(product.getImageBase64()));
        }
        if (product.getProductDetails() != null && !product.getProductDetails().isEmpty()) {
            product.getProductDetails().forEach(productDetail -> {
                if (productDetail.getImageBase64() != null) {
                    productDetail.setImage(ImageBase64.setImageBase64(productDetail.getImageBase64()));
                }
            });
        }
        return super.save(product);
    }
}
