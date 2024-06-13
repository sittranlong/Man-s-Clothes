package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.User;
import io.github.pudo58.base.entity.Wishlist;
import io.github.pudo58.base.repo.ProductRepo;
import io.github.pudo58.base.repo.WishListRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {
    private final WishListRepo wishListRepo;
    private final ProductRepo productRepo;

    @Override
    @Transactional(rollbackOn = IllegalArgumentException.class)
    public ResponseEntity<?> add(UUID productId) {
        User user = User.getContext();
        if (user == null) {
            throw new IllegalArgumentException("Bạn chưa đăng nhập");
        }
        productRepo.findById(productId).ifPresent(product -> {
            Wishlist wishlist = new Wishlist();
            wishlist.setUser(user);
            wishlist.setProduct(product);
            this.wishListRepo.save(wishlist);
        });
        return ResponseEntity.ok().body("Add vào danh sách yêu thích thành công");
    }

    @Override
    @Transactional(rollbackOn = IllegalArgumentException.class)
    public void remove(UUID id) {
        wishListRepo.deleteById(id);
    }

    @Override
    public List<Wishlist> findAll() {
        return wishListRepo.findAllByUserId(User.getContextId());
    }

    @Override
    @Transactional(rollbackOn = IllegalArgumentException.class)
    public void removeMultiple(List<UUID> productIds) {
        wishListRepo.deleteAllById(productIds);
    }
}
