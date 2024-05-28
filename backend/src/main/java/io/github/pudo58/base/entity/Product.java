package io.github.pudo58.base.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
public class Product extends BaseEntity {
    private String name;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    private Integer price;
    private Integer discountPercent;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;
    private String status;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Category.class)
    private Category category;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Brand.class)
    private Brand brand;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = MetaConfig.class)
    private MetaConfig metaConfig;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductDetail> productDetails = new ArrayList<>();

    // request insert
    @Transient
    private UUID categoryId;
    @Transient
    private UUID brandId;
    @Transient
    private String imageBase64;
}
