package io.github.pudo58.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Wishlist extends BaseEntity {
    @ManyToOne
    private User user;
    @ManyToOne
    private Product product;
}
