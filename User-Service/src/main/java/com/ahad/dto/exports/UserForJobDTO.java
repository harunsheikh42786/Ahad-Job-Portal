package com.ahad.dto.exports;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserForJobDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String headline;
}
