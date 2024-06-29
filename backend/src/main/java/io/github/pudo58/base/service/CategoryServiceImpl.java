package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.Category;
import io.github.pudo58.base.repo.CategoryRepo;
import io.github.pudo58.base.service.core.AbstractService;
import io.github.pudo58.dto.CategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends AbstractService<Category> implements CategoryService {
    private final CategoryRepo categoryRepo;
    @Override
    public Page<Category> findBySearch(CategoryRequest model) {
        return categoryRepo.findBySearch(model, PageRequest.of(model.getPage(), model.getSize()));
    }
}
