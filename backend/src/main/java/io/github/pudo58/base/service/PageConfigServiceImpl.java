package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.PageConfig;
import io.github.pudo58.base.repo.PageConfigRepo;
import io.github.pudo58.base.service.core.AbstractService;
import io.github.pudo58.constant.LocaleConst;
import io.github.pudo58.constant.PageConfigConstant;
import io.github.pudo58.dto.CommonRequest;
import io.github.pudo58.dto.PageConfigRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PageConfigServiceImpl extends AbstractService<PageConfig> implements PageConfigService {
    private final PageConfigRepo pageConfigRepo;

    @Override
    public ResponseEntity<?> findByCode(PageConfigRequest model) {
        if (PageConfigConstant.ABOUT_PAGE.equals(model.getCode())) {
            PageConfig pageConfig = pageConfigRepo.findByCodeAndLocale(model.getCode(), LocaleConst.getLocale(LocaleContextHolder.getLocale()));
            if (pageConfig != null) {
                Map<String, Object> data = Map.of(
                        "value", pageConfig.getValue(),
                        "description", pageConfig.getDescription() == null ? "" : pageConfig.getDescription()
                );
                return ResponseEntity.ok(data);
            } else {
                PageConfig defaultPageConfig = pageConfigRepo.findByCodeAndLocale(model.getCode(), LocaleConst.getLocale(LocaleConst.DEFAULT_LOCALE));
                if (defaultPageConfig != null) {
                    Map<String, Object> data = Map.of(
                            "value", defaultPageConfig.getValue(),
                            "description", defaultPageConfig.getDescription()
                    );
                    return ResponseEntity.ok(data);
                }
            }
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public Page<PageConfig> findBySearch(CommonRequest model) {
        return pageConfigRepo.findBySearch(model, model.getPageable());
    }
}
