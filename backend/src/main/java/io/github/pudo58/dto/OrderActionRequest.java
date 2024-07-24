package io.github.pudo58.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
public class OrderActionRequest {
    private UUID id;
    private String reason;
    private Date expectedDate;
}
