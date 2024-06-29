package io.github.pudo58.endpoint.v2;

import io.github.pudo58.base.entity.Category;
import io.github.pudo58.base.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v2/category")
@RequiredArgsConstructor
public class CategoryV2Controller {
    private final CategoryService categoryService;

    @GetMapping("/findAll")
    public List<Category> findAll() {
        return categoryService.findAll();
    }
}
