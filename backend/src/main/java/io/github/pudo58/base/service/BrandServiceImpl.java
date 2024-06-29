package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.Brand;
import io.github.pudo58.base.repo.BrandRepo;
import io.github.pudo58.base.service.core.AbstractService;
import io.github.pudo58.dto.CommonRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl extends AbstractService<Brand> implements BrandService {
    private final BrandRepo brandRepo;

    @Override
    public Page<Brand> findBySearch(CommonRequest model) {
        return brandRepo.findBySearch(model, model.getPageable());
    }
}
