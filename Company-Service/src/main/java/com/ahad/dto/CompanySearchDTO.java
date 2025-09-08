package com.ahad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanySearchDTO {
    private String name;
    private String headline;
    private Boolean open;
}
