package com.ahad.dto.imports;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyForJobDTO {
    private String id;
    private String name;
    private String headline;
    private String location;

}
