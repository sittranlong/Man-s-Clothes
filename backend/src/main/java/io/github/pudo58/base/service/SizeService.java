package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.Size;
import io.github.pudo58.base.service.core.Service;
import io.github.pudo58.dto.CommonRequest;
import org.springframework.data.domain.Page;

public interface SizeService extends Service<Size> {
    Page<Size> findBySearch(CommonRequest model);
}
