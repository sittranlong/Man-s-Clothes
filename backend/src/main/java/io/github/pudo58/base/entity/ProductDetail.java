package io.github.pudo58.base.entity;

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
    private Boolean isDeleted;

    @Override
    public void preUpdate() {
        synchronized (this) {
            if (quantity == null) {
                quantity = 0;
            }
            if (isDeleted == null) {
                isDeleted = false;
            }
            if (quantity < 0) {
                quantity = 0;
            }
        }
    }
}
