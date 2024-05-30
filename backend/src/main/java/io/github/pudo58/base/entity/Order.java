package io.github.pudo58.base.entity;

import io.github.pudo58.constant.OrderConst;
import io.github.pudo58.util.Random;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "orders")
public class Order extends BaseEntity {

    public Order() {

    }
    private String code;
    private Integer total;
    private String status;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne
    private Voucher voucher;
    private String address;
    private String phone;
    private String note;
    private Integer paymentMethod; // 1: COD, 2: QR code
    private Integer discount; // giảm giá
    private Integer shippingFee; // phí vận chuyển
    private Integer finalTotal; // tổng tiền cuối cùng

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails = new ArrayList<>();


    @Override
    public void prePersist() {
        this.code = Random.randomString(8);
        this.status = OrderConst.STATUS_PENDING;
    }

}
