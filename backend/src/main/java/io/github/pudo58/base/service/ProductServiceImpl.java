package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.Brand;
import io.github.pudo58.base.entity.Category;
import io.github.pudo58.base.entity.Product;
import io.github.pudo58.base.entity.ProductDetail;
import io.github.pudo58.base.repo.*;
import io.github.pudo58.base.service.core.AbstractService;
import io.github.pudo58.constant.ProductConst;
import io.github.pudo58.dto.ProductCard;
import io.github.pudo58.dto.ProductSearchRequest;
import io.github.pudo58.util.ImageBase64;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl extends AbstractService<Product> implements ProductService {
    private final ProductRepo productRepo;
    private final BrandRepo brandRepo;
    private final CategoryRepo categoryRepo;
    private final SizeRepo sizeRepo;
    private final ColorRepo colorRepo;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Product save(Product product) {
        addDetail(product);
        return super.save(product);
    }

    @Override
    public Product findById(UUID uuid) {
        Product product = super.findById(uuid);
        product.setProductDetails(product.getProductDetails().stream().filter(
                productDetail -> !Boolean.TRUE.equals(productDetail.getIsDeleted())
        ).sorted(Comparator.comparing(ProductDetail::getCreateDate)).toList());
        if (product.getBrand() != null) {
            product.setBrandId(product.getBrand().getId());
        }
        if (product.getCategory() != null) {
            product.setCategoryId(product.getCategory().getId());
        }
        if (product.getProductDetails() != null && !product.getProductDetails().isEmpty()) {
            product.getProductDetails().forEach(productDetail -> {
                if (productDetail.getSize() != null) {
                    productDetail.setSizeId(productDetail.getSize().getId());
                }
                if (productDetail.getColor() != null) {
                    productDetail.setColorId(productDetail.getColor().getId());
                }
            });
        }
        return product;
    }

    @Override
    public Product update(UUID uuid, Product product) {
        this.addDetail(product);
        return super.update(uuid, product);
    }

    @Override
    public void deleteById(UUID uuid) {
        Product product = productRepo.findById(uuid).orElse(null);
        if (product != null) {
            product.setStatus(ProductConst.STATUS_INACTIVE);
            this.productRepo.save(product);
        }
    }

    @Override
    public Page<Product> findBySearch(ProductSearchRequest model) {
        return productRepo.findBySearch(model, model.getPageable());
    }

    @Override
    public Page<ProductCard> findByFilter(ProductSearchRequest model) {
        Page<Product> productPage = productRepo.findAll((root, query, builder) -> {
            Join<Product, Brand> brandJoin = root.join("brand");
            Join<Product, Category> categoryJoin = root.join("category");
            final List<Predicate> predicates = new ArrayList<>();
            if (model.getBrandIdList() != null && !model.getBrandIdList().isEmpty()) {
                predicates.add(brandJoin.get("id").in(model.getBrandIdList()));
            }
            if (model.getCategoryIdList() != null && !model.getCategoryIdList().isEmpty()) {
                predicates.add(categoryJoin.get("id").in(model.getCategoryIdList()));
            }
            if (model.getKeyword() != null && !model.getKeyword().trim().isEmpty()) {
                predicates.add(builder.or(
                        builder.like(root.get("name"), "%" + model.getKeyword() + "%"),
                        builder.like(root.get("description"), "%" + model.getKeyword() + "%")
                ));
            }
            predicates.add(builder.equal(root.get("status"), ProductConst.STATUS_ACTIVE));
            query.orderBy(builder.desc(root.get("createDate")));
            return builder.and(predicates.toArray(new Predicate[0]));
        }, model.getPageable());
        List<Product> productList = new ArrayList<>(productPage.getContent());
        List<ProductCard> productCardList = new ArrayList<>();
        for (Product product : productList) {
            productCardList.add(ProductCard.fromProduct(product));
        }
        return productPage.map(product -> productCardList.get(productList.indexOf(product)));
    }

    @Override
    public void deleteDetail(UUID productDetailId) {
        Query query = entityManager.createQuery("UPDATE ProductDetail pd SET pd.isDeleted = true WHERE pd.id = :productDetailId");
        query.setParameter("productDetailId", productDetailId);
        query.executeUpdate();
    }

    private void addDetail(Product product) {
        if (product.getImageBase64() != null) {
            product.setImage(ImageBase64.setImageBase64(product.getImageBase64()));
        }
        if (product.getProductDetails() != null && !product.getProductDetails().isEmpty()) {
            product.getProductDetails().forEach(productDetail -> {
                productDetail.setProduct(product);
                if (productDetail.getImageBase64() != null) {
                    productDetail.setImage(ImageBase64.setImageBase64(productDetail.getImageBase64()));
                }
                if (productDetail.getSizeId() != null) {
                    sizeRepo.findById(productDetail.getSizeId()).ifPresent(productDetail::setSize);
                }
                if (productDetail.getColorId() != null) {
                    colorRepo.findById(productDetail.getColorId()).ifPresent(productDetail::setColor);
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
