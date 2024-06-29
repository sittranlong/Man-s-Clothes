package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.Product;
import io.github.pudo58.base.repo.BrandRepo;
import io.github.pudo58.base.repo.CategoryRepo;
import io.github.pudo58.base.repo.ProductRepo;
import io.github.pudo58.base.service.core.AbstractService;
import io.github.pudo58.dto.ProductSearchRequest;
import io.github.pudo58.util.ImageBase64;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl extends AbstractService<Product> implements ProductService {
    private final ProductRepo productRepo;
    private final BrandRepo brandRepo;
    private final CategoryRepo categoryRepo;

    @Override
    public Product save(Product product) {
        addDetail(product);
        return super.save(product);
    }

    @Override
    public Product findById(UUID uuid) {
        Product product = super.findById(uuid);
        if (product != null) {
            if (product.getBrand() != null) {
                product.setBrandId(product.getBrand().getId());
            }
            if (product.getCategory() != null) {
                product.setCategoryId(product.getCategory().getId());
            }
        }
        return product;
    }

    @Override
    public Product update(UUID uuid, Product product) {
        this.addDetail(product);
        return super.update(uuid, product);
    }

    @Override
    public Page<Product> findBySearch(ProductSearchRequest model) {
        return productRepo.findBySearch(model, PageRequest.of(model.getPage(), model.getSize()));
    }

    private void addDetail(Product product) {
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
        if (product.getBrandId() != null) {
            brandRepo.findById(product.getBrandId()).ifPresent(product::setBrand);
        }
        if (product.getCategoryId() != null) {
            categoryRepo.findById(product.getCategoryId()).ifPresent(product::setCategory);
        }
    }
}
