package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.Color;
import io.github.pudo58.base.service.core.Service;
import io.github.pudo58.dto.CommonRequest;
import org.springframework.data.domain.Page;

public interface ColorService extends Service<Color> {
    Page<Color> findBySearch(CommonRequest model);
}
