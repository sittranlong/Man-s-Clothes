package io.github.pudo58.base.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Cart extends BaseEntity {
    private Integer quantity;
    private Integer price;

    @ManyToOne
    private ProductDetail productDetail;

    @ManyToOne
    @JsonIgnore
    private User user;

}
