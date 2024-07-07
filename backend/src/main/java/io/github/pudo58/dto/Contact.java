package io.github.pudo58.dto;

import io.github.pudo58.base.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class Contact {
    private String name;
    private String phone;
    private String address;
    private User user;
    private List<UUID> cartIdList;
}
