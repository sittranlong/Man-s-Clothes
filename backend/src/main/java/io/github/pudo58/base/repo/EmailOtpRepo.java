package io.github.pudo58.base.repo;

import io.github.pudo58.base.entity.EmailOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmailOtpRepo extends JpaRepository<EmailOtp, UUID> {
    List<EmailOtp> findByEmailOrderByCreateDateDesc(String email);
}
