package io.github.pudo58.endpoint;

import io.github.pudo58.base.controller.AbstractController;
import io.github.pudo58.base.entity.Brand;
import io.github.pudo58.base.service.BrandService;
import io.github.pudo58.dto.CategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandController extends AbstractController<Brand> {
    private final BrandService brandService;

    @PostMapping("/findBySearch")
    public Page<Brand> findBySearch(@RequestBody CategoryRequest model) {
        return brandService.findBySearch(model);
    }

}