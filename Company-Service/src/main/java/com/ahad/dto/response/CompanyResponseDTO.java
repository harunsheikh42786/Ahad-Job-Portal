package com.ahad.dto.response;

import java.util.UUID;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyResponseDTO {
    private UUID id;
    private String name;
    private String email;
    private boolean open;
}
