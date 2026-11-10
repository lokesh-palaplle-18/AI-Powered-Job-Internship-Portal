package com.jobportal.service;

import com.jobportal.dto.request.RegisterRequest;
import com.jobportal.dto.response.UserResponse;

public interface UserService {
    UserResponse register(RegisterRequest request);

    UserResponse getUserById(Long userId);
}
