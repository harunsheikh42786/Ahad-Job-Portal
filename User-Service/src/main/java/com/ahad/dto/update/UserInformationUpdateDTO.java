package com.ahad.dto.update;

import com.ahad.enums.JobStatus;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInformationUpdateDTO {

    private String headline;

    private String portfolio;

    private JobStatus jobStatus;
}
