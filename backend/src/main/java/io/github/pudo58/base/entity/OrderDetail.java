package io.github.pudo58.base.entity;

import io.github.pudo58.constant.OrderConst;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

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

    public static List<OrderDetail> fromCartList(List<Cart> cartList) {
        return cartList.stream().map(cart -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setPrice(cart.getProductDetail().getProduct().getPrice());
            orderDetail.setQuantity(cart.getQuantity());
            orderDetail.setTotal(cart.getProductDetail().getProduct().getPrice() * cart.getQuantity());
            orderDetail.setStatus(OrderConst.STATUS_PENDING);
            orderDetail.setProductDetail(cart.getProductDetail());
            return orderDetail;
        }).collect(Collectors.toList());
    }
}
