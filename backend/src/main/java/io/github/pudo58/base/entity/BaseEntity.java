package io.github.pudo58.base.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Date createDate = new Date();
    private Date modifiedDate = new Date();
    private String createBy;

    @PreUpdate
    public abstract void preUpdate();

    @PrePersist
    public abstract void prePersist();

    protected void nothingToDo() {
    }
}
