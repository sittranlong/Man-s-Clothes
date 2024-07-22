package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.Voucher;
import io.github.pudo58.base.service.core.Service;
import io.github.pudo58.dto.CommonRequest;
import org.springframework.data.domain.Page;

public interface VoucherService extends Service<Voucher> {
    Page<Voucher> findBySearch(CommonRequest model);
}
