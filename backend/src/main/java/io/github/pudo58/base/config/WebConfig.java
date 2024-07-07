package io.github.pudo58.base.config;

import io.github.pudo58.base.entity.Role;
import io.github.pudo58.base.entity.User;
import io.github.pudo58.base.override.CustomAuthenticationEntryPoint;
import io.github.pudo58.base.repo.RoleRepo;
import io.github.pudo58.base.repo.UserRepo;
import io.github.pudo58.constant.UserConst;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Configuration
@Slf4j
@RequiredArgsConstructor
@EnableWebMvc
public class WebConfig {
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;

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

            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder().encode("123456"));
            user.setRoleList(new HashSet<>(roleList));
            user.setStatus(UserConst.STATUS_ACTIVE);

            if (this.userRepo.findByUsername(user.getUsername()) == null) {
                this.userRepo.save(user);
            }
        };
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:message/messages");
        messageSource.setCacheSeconds(10); //reload messages every 10 seconds
        messageSource.setDefaultLocale(new Locale("vi"));
        return messageSource;
    }
}
