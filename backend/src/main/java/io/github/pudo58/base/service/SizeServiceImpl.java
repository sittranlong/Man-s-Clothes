package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.Size;
import io.github.pudo58.base.repo.SizeRepo;
import io.github.pudo58.base.service.core.AbstractService;
import io.github.pudo58.dto.CommonRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SizeServiceImpl extends AbstractService<Size> implements SizeService{
    private final SizeRepo sizeRepo;
    @Override
    public Page<Size> findBySearch(CommonRequest model) {
        return sizeRepo.findBySearch(model, model.getPageable());
    }
}
