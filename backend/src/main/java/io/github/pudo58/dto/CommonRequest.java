package io.github.pudo58.dto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Date;

@Setter
@Getter
public class CommonRequest extends PageParam {
    private String keyword;
    private Date startDate;
    private Date endDate;

    public Pageable getPageable() {
        return PageRequest.of(getPage(), getSize());
    }
}
