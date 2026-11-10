package com.jobportal.dto.response;

import com.jobportal.enums.Role;
import lombok.Data;

@Data
public class UserResponse {
    private Long id;

    private String name;

    private String email;

    private Role role;

    private String skills;

    private String companyName;

    private String website;
}
