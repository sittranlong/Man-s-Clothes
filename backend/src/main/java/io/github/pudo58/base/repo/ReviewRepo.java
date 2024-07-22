package io.github.pudo58.base.repo;

import io.github.pudo58.base.entity.Review;
import io.github.pudo58.base.entity.Size;
import io.github.pudo58.dto.CommonRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepo extends JpaRepository<Review, UUID> {
    @Query("SELECT r FROM Review r WHERE r.product.id = :productId and r.status = 'APPROVED'")
    List<Review> findByProductId(UUID productId);

    @Query("""
            select s from Review s where length(:#{#model.keyword}) = 0 or s.content like %:#{#model.keyword}%
            order by s.createDate desc
            """)
    Page<Review> findBySearch(CommonRequest model, Pageable pageable);

    @Query("SELECT case when count(order) = 0 then false else true end FROM Order order " +
            "join order.orderDetails details " +
            "where order.user.id = :userId and details.productDetail.product.id = :productId" +
            " and order.status = 'COMPLETED'")
    boolean isBuy(UUID productId, UUID userId);
}
