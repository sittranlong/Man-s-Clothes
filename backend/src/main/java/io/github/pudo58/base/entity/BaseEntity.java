package io.github.pudo58.base.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Date createDate = new Date();
    private Date modifiedDate = new Date();
    private String createBy;

    @PreUpdate
    public void preUpdate(){

    }

    @PrePersist
    public  void prePersist(){

    }

    protected void nothingToDo() {
    }
}
