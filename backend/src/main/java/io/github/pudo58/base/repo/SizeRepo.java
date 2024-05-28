package io.github.pudo58.base.repo;

import io.github.pudo58.base.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SizeRepo extends JpaRepository<Size, UUID> {
}
