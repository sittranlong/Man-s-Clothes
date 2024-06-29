package io.github.pudo58.endpoint;

import io.github.pudo58.base.controller.AbstractController;
import io.github.pudo58.base.entity.Size;
import io.github.pudo58.base.service.SizeService;
import io.github.pudo58.dto.CommonRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/size")
@RequiredArgsConstructor
public class SizeController extends AbstractController<Size> {
    private final SizeService sizeService;

    @PostMapping("/findBySearch")
    public Page<Size> findBySearch(@RequestBody CommonRequest model) {
        return sizeService.findBySearch(model);
    }
}
