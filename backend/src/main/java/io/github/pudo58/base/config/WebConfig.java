package io.github.pudo58.base.config;

import io.github.pudo58.base.entity.Role;
import io.github.pudo58.base.override.CustomAuthenticationEntryPoint;
import io.github.pudo58.base.repo.RoleRepo;
import io.github.pudo58.constant.UserConst;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class WebConfig {
    private final RoleRepo roleRepo;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    @Transactional
    public CommandLineRunner commandLineRunner() {
        return args -> {
            List<Role> roleList = this.roleRepo.findAll((root, query, builder) -> builder.in(root.get("name")).value(UserConst.ROLE_LIST));
            if (roleList.isEmpty()) {
                for (String role : UserConst.ROLE_LIST) {
                    Role roleEntity = new Role();
                    roleEntity.setName(role);
                    this.roleRepo.save(roleEntity);
                }
            }
        };
    }
}
