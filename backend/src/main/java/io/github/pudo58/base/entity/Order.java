package io.github.pudo58.base.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.github.pudo58.constant.OrderConst;
import io.github.pudo58.util.Random;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "orders")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Order extends BaseEntity {
    public Order() {
    }
    private String code;
    private String name;
    private Integer total;
    private String status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Voucher voucher;
    private String address;
    private String phone;
    private String note;
    private Integer paymentMethod; // 1: COD, 2: QR code
    private Integer discount; // giảm giá
    private Integer shippingFee; // phí vận chuyển
    private Integer finalTotal; // tổng tiền cuối cùng
    private Date deliveryDate; // ngày giao hàng
    private Date expectedDeliveryDate; // ngày nhận dự kiến

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<OrderDetail> orderDetails = new ArrayList<>();


    @Override
    public void prePersist() {
        this.code = Random.randomString(8);
    }

}
