package io.github.pudo58.dto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Setter
@Getter
public class CommonRequest extends PageParam {
    private String keyword;

    public Pageable getPageable() {
        return PageRequest.of(getPage(), getSize());
    }
}
