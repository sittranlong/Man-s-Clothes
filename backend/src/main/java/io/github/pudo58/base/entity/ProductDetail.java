package io.github.pudo58.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Setter
@Getter
public class ProductDetail extends BaseEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    private Size size;
    @ManyToOne(cascade = CascadeType.ALL)
    private Color color;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Product product;
    private Integer quantity;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    // request insert
    @Transient
    private UUID sizeId;
    @Transient
    private UUID colorId;
    @Transient
    private String imageBase64;
}
