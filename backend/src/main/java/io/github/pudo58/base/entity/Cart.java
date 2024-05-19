package io.github.pudo58.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Cart extends BaseEntity {
    private Integer quantity;
    private Integer price;

    @ManyToOne
    private ProductDetail productDetail;

    @ManyToOne
    private User user;

}
