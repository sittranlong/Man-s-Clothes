package io.github.pudo58.endpoint;

import io.github.pudo58.base.controller.AbstractController;
import io.github.pudo58.base.entity.Color;
import io.github.pudo58.base.service.ColorService;
import io.github.pudo58.dto.CommonRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/color")
@RequiredArgsConstructor
public class ColorController extends AbstractController<Color> {
    private final ColorService colorService;

    @PostMapping("/findBySearch")
    public Page<Color> findBySearch(@RequestBody CommonRequest model) {
        return colorService.findBySearch(model);
    }

}
