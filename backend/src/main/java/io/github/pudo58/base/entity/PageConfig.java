package io.github.pudo58.base.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class PageConfig extends BaseEntity {
    private String name;
    private String code;
    private String value;
    private String description;
    private String status;
    private String type;
}
