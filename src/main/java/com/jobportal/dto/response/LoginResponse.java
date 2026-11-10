
package com.jobportal.dto.response;

import com.jobportal.enums.Role;
import lombok.Data;

@Data
public class LoginResponse {

    private String accessToken;

    private String tokenType;

    private long expiresIn;

    private String email;

    private Role role;
}
