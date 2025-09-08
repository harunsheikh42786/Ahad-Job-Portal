package com.ahad.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import com.ahad.enums.JobStatus;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user_information")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInformation {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(length = 50)
    private String headline;

    private String portfolio; // link to portfolio / resume website

    @OneToMany(mappedBy = "userInformation", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Education> educations;

    @Enumerated(EnumType.STRING)
    private JobStatus jobStatus; // OPEN_TO_WORK, EMPLOYED, NOT_LOOKING

    @OneToMany(mappedBy = "userInformation", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<JobHistory> jobHistories;

    @OneToMany(mappedBy = "userInformation", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Achievement> achievements;

    @OneToOne(mappedBy = "userInformation", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Address address;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;
}
