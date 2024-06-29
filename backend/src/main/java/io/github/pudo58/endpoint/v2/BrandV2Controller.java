package io.github.pudo58.endpoint.v2;

import io.github.pudo58.base.entity.Brand;
import io.github.pudo58.base.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v2/brand")
@RequiredArgsConstructor
public class BrandV2Controller {
    private final BrandService brandService;

    @GetMapping("/findAll")
    public List<Brand> findAll() {
        return brandService.findAll();
    }
}
