package io.github.pudo58.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Setter
@Getter
public class Review extends BaseEntity {
    private String content;
    private Integer rating;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    @Transient
    private String imageBase64;

    @ManyToOne
    private User user;
    @ManyToOne
    private Product product;
    @Transient
    private UUID productId;
    private String status;
}
