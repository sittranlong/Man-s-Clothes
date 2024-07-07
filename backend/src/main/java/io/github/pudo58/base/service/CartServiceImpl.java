package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.Cart;
import io.github.pudo58.base.entity.ProductDetail;
import io.github.pudo58.base.entity.User;
import io.github.pudo58.base.repo.CartRepo;
import io.github.pudo58.base.repo.ProductDetailRepo;
import io.github.pudo58.dto.CartRequest;
import io.github.pudo58.util.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepo cartRepo;
    private final ProductDetailRepo productDetailRepo;
    private final Message message;

    @Override
    public ResponseEntity<?> add(CartRequest model) {
        this.validate(model);
        ProductDetail productDetail = this.productDetailRepo.getById(model.getProductDetailId()).orElse(null);
        if (productDetail == null) {
            return ResponseEntity.badRequest().body(this.message.getMessage("product.not-found"));
        }
        if (model.getQuantity() > productDetail.getQuantity()) {
            return ResponseEntity.badRequest().body(this.message.getMessage("quantity.not-enough"));
        }
        Cart cart = this.cartRepo.findByUserIdAndProductDetailId(User.getContext().getId(), model.getProductDetailId());
        if (cart == null) {
            cart = new Cart();
            cart.setProductDetail(productDetail);
            cart.setQuantity(model.getQuantity());
            cart.setPrice(productDetail.getProduct().getPrice() * model.getQuantity());
            cart.setUser(User.getContext());
        } else {
            cart.setQuantity(cart.getQuantity() + model.getQuantity());
            cart.setPrice(cart.getPrice() + (productDetail.getProduct().getPrice() * model.getQuantity()));
        }
        this.cartRepo.save(cart);
        return ResponseEntity.ok().body(this.message.getMessage("cart.added"));
    }

    @Override
    public Page<Cart> get(CartRequest model) {
        return this.cartRepo.findByUserId(User.getContextId(), model.getPageable());
    }

    @Override
    public void delete(UUID id) {
        this.cartRepo.deleteById(id);
    }

    @Override
    public ResponseEntity<?> update(CartRequest model) {
        Cart cart = this.cartRepo.findById(model.getCartId()).orElse(null);
        if (cart == null) {
            return ResponseEntity.badRequest().body(this.message.getMessage("cart.not-found"));
        }
        this.validate(model);
        ProductDetail productDetail = this.productDetailRepo.getById(cart.getProductDetail().getId()).orElse(null);
        if (productDetail == null) {
            return ResponseEntity.badRequest().body(this.message.getMessage("product.not-found"));
        }
        if (model.getQuantity() > productDetail.getQuantity()) {
            return ResponseEntity.badRequest().body(this.message.getMessage("quantity.not-enough"));
        }
        cart.setProductDetail(productDetail);
        cart.setQuantity(model.getQuantity());
        cart.setPrice(productDetail.getProduct().getPrice() * model.getQuantity());
        this.cartRepo.save(cart);
        return ResponseEntity.ok().body(this.message.getMessage("cart.updated"));
    }

    private void validate(CartRequest model) {
        if (model.getQuantity() <= 0) {
            throw new IllegalArgumentException(this.message.getMessage("quantity.invalid"));
        }

    }
}
