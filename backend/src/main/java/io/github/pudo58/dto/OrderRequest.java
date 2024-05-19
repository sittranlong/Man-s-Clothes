package io.github.pudo58.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class OrderRequest {
    private UUID proxyTypeId;
    private int quantity;
}
