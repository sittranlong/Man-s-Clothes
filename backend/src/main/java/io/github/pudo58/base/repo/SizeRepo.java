package io.github.pudo58.base.repo;

import io.github.pudo58.base.entity.Size;
import io.github.pudo58.dto.CommonRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SizeRepo extends JpaRepository<Size, UUID> {
    @Query("""
            select s from Size s where length(:#{#model.keyword}) = 0 or s.name like %:#{#model.keyword}%
            order by s.createDate desc
            """)
    Page<Size> findBySearch(CommonRequest model, Pageable pageable);
}
