package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.Brand;
import io.github.pudo58.base.service.core.Service;
import io.github.pudo58.dto.CategoryRequest;
import org.springframework.data.domain.Page;

public interface BrandService extends Service<Brand> {
    Page<Brand> findBySearch(CategoryRequest model);
}
