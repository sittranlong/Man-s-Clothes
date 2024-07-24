package io.github.pudo58.base.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

@Entity
@Setter
@Getter
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code", "locale"})
})
public class PageConfig extends BaseEntity {
    private String name;
    private String code;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String value;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    private String status = "ACTIVE";
    private String type;
    private Locale locale; // en, vi, zh
}
