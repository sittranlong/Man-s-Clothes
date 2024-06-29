package io.github.pudo58.endpoint;

import io.github.pudo58.base.controller.AbstractController;
import io.github.pudo58.base.entity.Category;
import io.github.pudo58.base.service.CategoryService;
import io.github.pudo58.dto.CategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController extends AbstractController<Category> {
    private final CategoryService categoryService;

    @PostMapping("/findBySearch")
    public Page<Category> findBySearch(@RequestBody CategoryRequest model){
        return categoryService.findBySearch(model);
    }

}
