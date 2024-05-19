package io.github.pudo58.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class PostCommend extends BaseEntity {
    private String commend;
    private Integer like;
    private Integer dislike;
    @ManyToOne
    private Post post;
}
