package io.github.pudo58.base.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class ProductDetail extends BaseEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    private Size size;
    @ManyToOne(cascade = CascadeType.ALL)
    private Color color;

    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;
    private Integer quantity;
    private byte[] image;
}
