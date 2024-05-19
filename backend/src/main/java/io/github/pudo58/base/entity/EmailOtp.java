package io.github.pudo58.base.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
public class EmailOtp extends BaseEntity{
    private String email;
    private String otp;
    private boolean verified;
    private Date expiryDate;
    @Override
    public void preUpdate() {

    }

    @Override
    public void prePersist() {
        this.verified = false;
    }
}
