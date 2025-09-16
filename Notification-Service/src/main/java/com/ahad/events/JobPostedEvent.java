package com.ahad.events;

import java.util.UUID;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobPostedEvent {
    private UUID id;
    private String title;
    private UUID companyId;
    private String description; // optional, short summary
    private String location; // optional
    private String experienceLevel; // optional
}
