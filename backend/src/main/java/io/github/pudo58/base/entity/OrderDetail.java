package io.github.pudo58.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class OrderDetail extends BaseEntity {
    private Integer price;
    private Integer quantity;
    private Integer total;
    private String status;
    @ManyToOne
    private Order order;
    @ManyToOne
    private ProductDetail productDetail;
}
