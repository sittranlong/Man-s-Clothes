package io.github.pudo58.endpoint;

import io.github.pudo58.base.controller.AbstractController;
import io.github.pudo58.base.entity.PageConfig;
import io.github.pudo58.base.service.PageConfigService;
import io.github.pudo58.dto.CommonRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/page-config")
@RequiredArgsConstructor
public class PageConfigController extends AbstractController<PageConfig> {
    private final PageConfigService pageConfigService;

    @PostMapping("/findBySearch")
    public Page<PageConfig> findBySearch(@RequestBody CommonRequest model) {
        return pageConfigService.findBySearch(model);
    }
}
