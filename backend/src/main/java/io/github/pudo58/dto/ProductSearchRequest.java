package io.github.pudo58.dto;

import io.github.pudo58.constant.ProductConst;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class ProductSearchRequest extends CommonRequest {
    private String status = ProductConst.STATUS_INACTIVE;
    private List<UUID> categoryIdList;
    private List<UUID> brandIdList;
}
