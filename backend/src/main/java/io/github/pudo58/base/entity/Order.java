package io.github.pudo58.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "orders")
public class Order extends BaseEntity {
    private String code;
    private Integer total;
    private int quantity;
    private String status;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // danh sách sản phẩm trong đơn hàng
    @Override
    public void preUpdate() {

    }

    @Override
    public void prePersist() {

    }
}
