package io.github.pudo58.base.repo;

import io.github.pudo58.base.entity.Brand;
import io.github.pudo58.dto.CommonRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BrandRepo extends JpaRepository<Brand, UUID> {

    @Query("""
    select b from Brand b where length(:#{#model.keyword}) = 0 or b.name like %:#{#model.keyword}%
    """)
    Page<Brand> findBySearch(CommonRequest model, Pageable pageable);
}
