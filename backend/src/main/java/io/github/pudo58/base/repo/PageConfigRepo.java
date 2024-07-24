package io.github.pudo58.base.repo;

import io.github.pudo58.base.entity.Color;
import io.github.pudo58.base.entity.PageConfig;
import io.github.pudo58.dto.CommonRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Locale;
import java.util.UUID;

@Repository
public interface PageConfigRepo extends JpaRepository<PageConfig, UUID> {
    @Query("SELECT pc FROM PageConfig pc WHERE pc.code = :code AND pc.locale = :locale and pc.status = 'ACTIVE'")
    PageConfig findByCodeAndLocale(String code, Locale locale);

    @Query("""
            select c from PageConfig c where length(:#{#model.keyword}) = 0 or c.name like %:#{#model.keyword}%
            """)
    Page<PageConfig> findBySearch(CommonRequest model, Pageable pageable);
}
