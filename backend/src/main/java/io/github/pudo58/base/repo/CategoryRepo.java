package io.github.pudo58.base.repo;

import io.github.pudo58.base.entity.Category;
import io.github.pudo58.dto.CommonRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepo extends JpaRepository<Category, UUID> {
    @Query("""
    select c from Category c where 
    :#{#model.keyword} is null or length(:#{#model.keyword}) = 0 or c.name like %:#{#model.keyword}%
    """)
    Page<Category> findBySearch(CommonRequest model, Pageable pageable);
}
