package io.github.pudo58.base.repo;

import io.github.pudo58.base.entity.Product;
import io.github.pudo58.dto.ProductSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepo extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

    @Query("""
        select p from Product p
        where (length(:#{#model.keyword}) = 0 or p.name like %:#{#model.keyword}%)
        and p.status <> :#{#model.status}
        order by p.createDate desc
""")
    Page<Product> findBySearch(ProductSearchRequest model , Pageable pageable);
}
