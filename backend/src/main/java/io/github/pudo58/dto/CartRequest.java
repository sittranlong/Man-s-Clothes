package io.github.pudo58.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class CartRequest extends PageParam{
    private UUID productDetailId;
    private int quantity;
    private UUID cartId;
}
