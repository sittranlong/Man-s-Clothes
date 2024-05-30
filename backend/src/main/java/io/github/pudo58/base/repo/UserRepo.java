package io.github.pudo58.base.repo;

import io.github.pudo58.base.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {
    User findByUsername(String username);

    @Query("""
            select u from User u
            left join u.roleList r
            where u.username = :username
    """)
    User getByUsername(String username);
}
