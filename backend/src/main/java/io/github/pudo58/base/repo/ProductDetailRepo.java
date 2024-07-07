package io.github.pudo58.base.repo;

import io.github.pudo58.base.entity.ProductDetail;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductDetailRepo extends CrudRepository<ProductDetail, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ProductDetail> getById(UUID id);

    List<ProductDetail> findAllByProductId(UUID productId);
}
