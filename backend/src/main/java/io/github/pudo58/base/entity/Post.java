package io.github.pudo58.base.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Post extends BaseEntity {
    private String title;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;
    private Integer view;
    @Column(name = "like_count")
    private Integer like;
    private Integer dislike;
}
