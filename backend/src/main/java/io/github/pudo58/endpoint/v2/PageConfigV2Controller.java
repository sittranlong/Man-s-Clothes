package io.github.pudo58.endpoint.v2;

import io.github.pudo58.base.service.PageConfigService;
import io.github.pudo58.dto.PageConfigRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/page-config")
@RequiredArgsConstructor
public class PageConfigV2Controller {
    private final PageConfigService pageConfigService;

    @PostMapping("/findByCode")
    public ResponseEntity<?> findByCode(@RequestBody PageConfigRequest model) {
        return pageConfigService.findByCode(model);
    }
}
