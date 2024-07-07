package io.github.pudo58.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;

public class PageParam implements Serializable {
    private Integer page;
    private Integer size;

    public Integer getPage() {
        if (page == null)
            return 0;
        return Math.max(0, page);
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        if (size == null)
            return 0;
        return Math.max(0, size);
    }

    public void setSize(Integer size) {
        this.size = size;
    }
    public Pageable getPageable() {
        return PageRequest.of(getPage(), getSize());
    }
}
