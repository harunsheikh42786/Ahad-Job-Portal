package com.ahad.dto.imports;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSearchForCompanyhDTO {

    private String id;
    private String firstName;
    private String lastName;
    private String position;
    private LocalDate startDate;

}
