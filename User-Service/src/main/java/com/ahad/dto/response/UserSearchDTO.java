package com.ahad.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String headline;
}
