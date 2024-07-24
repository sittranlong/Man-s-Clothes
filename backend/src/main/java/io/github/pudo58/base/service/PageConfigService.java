package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.PageConfig;
import io.github.pudo58.base.service.core.Service;
import io.github.pudo58.dto.CommonRequest;
import io.github.pudo58.dto.PageConfigRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface PageConfigService extends Service<PageConfig> {
    ResponseEntity<?> findByCode(PageConfigRequest model);

    Page<PageConfig> findBySearch(CommonRequest model);
}
