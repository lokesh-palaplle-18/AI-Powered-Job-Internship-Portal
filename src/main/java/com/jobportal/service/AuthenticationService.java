package com.jobportal.service;

import com.jobportal.dto.request.LoginRequest;
import com.jobportal.dto.response.LoginResponse;

public interface AuthenticationService {
    LoginResponse login(LoginRequest request);
}
