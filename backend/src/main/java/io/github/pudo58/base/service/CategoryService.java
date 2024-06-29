package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.Category;
import io.github.pudo58.base.service.core.Service;
import io.github.pudo58.dto.CommonRequest;
import org.springframework.data.domain.Page;

public interface CategoryService extends Service<Category> {
    Page<Category> findBySearch(CommonRequest model);
}
