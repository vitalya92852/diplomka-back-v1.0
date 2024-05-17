package com.example.demo1222.dto.aim;

import lombok.Data;

@Data
public class EditAim {
    private String aimName;
    private String aimDescription;
    private Long userId;
    private Long aimId;
}
