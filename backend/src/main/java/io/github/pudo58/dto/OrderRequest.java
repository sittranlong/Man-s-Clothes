package io.github.pudo58.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class OrderRequest {
    private UUID orderId;
    private String voucherCode;
    private String reason;
    private String address;
    private String phone;
    private String note;
    private Integer paymentMethod;
}
