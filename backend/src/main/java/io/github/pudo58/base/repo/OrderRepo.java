package io.github.pudo58.base.repo;

import io.github.pudo58.base.entity.Color;
import io.github.pudo58.base.entity.Order;
import io.github.pudo58.dto.CommonRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface OrderRepo extends JpaRepository<Order, UUID> {

    Order getByIdAndStatusIn(UUID id, Collection<String> status);

    @Query("""
            select o from Order o  where 
            (length(:#{#model.keyword}) = 0 or o.code like %:#{#model.keyword}%)
            and (:#{#model.startDate} is null or o.createDate >= :#{#model.startDate})
            and (:#{#model.endDate} is null or o.createDate <= :#{#model.endDate})
            """)
    Page<Order> findBySearch(CommonRequest model, Pageable pageable);
}
