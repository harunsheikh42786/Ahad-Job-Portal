package com.ahad.dto;

import lombok.Data;

@Data
public class UserSearchDTO {

    private String id;
    private String firstName;
    private String lastName;
    private String headline; // 👈 better name than description

}
