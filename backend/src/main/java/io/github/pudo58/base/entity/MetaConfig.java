package io.github.pudo58.base.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class MetaConfig extends BaseEntity {
    // meta config seo for product
    private String title;
    private String description;
    private String keywords;
    private String slug;
    private byte[] image;
    private String imageBase64;
}
