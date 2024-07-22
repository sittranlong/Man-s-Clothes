package io.github.pudo58.base.repo;

import io.github.pudo58.base.entity.Size;
import io.github.pudo58.base.entity.Voucher;
import io.github.pudo58.dto.CommonRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoucherRepo extends JpaRepository<Voucher, UUID> {
    Optional<Voucher> findByCode(String code);

    @Query("""
            select s from Voucher s where length(:#{#model.keyword}) = 0 or s.name like %:#{#model.keyword}%
            or s.code like %:#{#model.keyword}%
            """)
    Page<Voucher> findBySearch(CommonRequest model, Pageable pageable);

}
