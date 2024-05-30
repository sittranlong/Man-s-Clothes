package io.github.pudo58.base.repo;

import io.github.pudo58.base.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoucherRepo extends JpaRepository<Voucher, UUID> {
    Optional<Voucher> findByCode(String code);
}
