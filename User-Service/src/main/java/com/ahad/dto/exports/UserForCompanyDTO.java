package com.ahad.dto.exports;

import java.time.LocalDate;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserForCompanyDTO {

    private String id;
    private String firstName;
    private String lastName;
    private String position;
    private LocalDate startDate;

}
