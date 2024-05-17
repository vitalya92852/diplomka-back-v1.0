package com.example.demo1222.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AimEdit extends AimStatus{
    private String description;
    private Long aimId;
}
