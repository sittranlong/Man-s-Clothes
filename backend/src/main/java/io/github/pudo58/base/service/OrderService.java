package io.github.pudo58.base.service;

import io.github.pudo58.dto.OrderRequest;
import io.github.pudo58.record.AlertResponseRecord;

public interface OrderService {
    AlertResponseRecord orderProxy(OrderRequest orderRequest);
}
