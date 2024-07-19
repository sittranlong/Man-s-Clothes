package io.github.pudo58.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.pudo58.base.exception.UnauthorizedException;
import io.github.pudo58.constant.UserConst;
import jakarta.persistence.*;
import jakarta.persistence.criteria.Join;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.util.*;

@Entity
@Setter
@Getter
public class User extends BaseEntity implements UserDetails {
    @Column(unique = true)
    private String username;
    @JsonIgnore
    private String password;
    private String fullName;
    @Column(unique = true)
    private String email;
    private String phone;
    private String address;
    private Boolean isEnable;
    private Integer status;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] avatar;

    @Transient
    private String avatarBase64;
    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roleList = new HashSet<>();

    public User() {
    }

    @Override
    public void prePersist() {
        this.isEnable = Boolean.FALSE;
        this.status = UserConst.STATUS_NON_EMAIL_VERIFIED;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role role : roleList) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return grantedAuthorities;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static User getContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            if (authentication.getPrincipal() instanceof User) {
                return (User) authentication.getPrincipal();
            }
        }
        throw new UnauthorizedException("Bạn chưa đăng nhập");
    }

    public static UUID getContextId() {
        User user = getContext();
        Assert.notNull(user, "Bạn chưa đăng nhập");
        return Optional.of(user.getId()).orElse(null);
    }

}
