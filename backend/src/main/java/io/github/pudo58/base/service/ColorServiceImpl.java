package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.Color;
import io.github.pudo58.base.repo.ColorRepo;
import io.github.pudo58.base.service.core.AbstractService;
import io.github.pudo58.dto.CommonRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl extends AbstractService<Color> implements ColorService {
    private final ColorRepo colorRepo;

    @Override
    public Page<Color> findBySearch(CommonRequest model) {
        return colorRepo.findBySearch(model, model.getPageable());
    }
}
