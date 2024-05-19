package io.github.pudo58.base.service;

import io.github.pudo58.base.repo.OrderRepo;
import io.github.pudo58.dto.OrderRequest;
import io.github.pudo58.record.AlertResponseRecord;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo repo;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public AlertResponseRecord orderProxy(OrderRequest model) {
        return new AlertResponseRecord("Đặt hàng thành công", HttpStatus.OK.value());
    }
}
