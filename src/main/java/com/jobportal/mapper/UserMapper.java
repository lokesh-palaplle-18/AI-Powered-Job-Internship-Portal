package com.jobportal.mapper;

import com.jobportal.dto.request.RegisterRequest;
import com.jobportal.dto.response.UserResponse;
import com.jobportal.entity.User;

public class UserMapper {


    private UserMapper() {
    }

    // Convert RegisterRequest DTO into User Entity
    public static User toEntity(RegisterRequest request) {

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        user.setSkills(request.getSkills());
        user.setCompanyName(request.getCompanyName());
        user.setWebsite(request.getWebsite());

        return user;
    }

    // Convert User Entity into UserResponse DTO
    public static UserResponse toResponse(User user) {

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setSkills(user.getSkills());
        response.setCompanyName(user.getCompanyName());
        response.setWebsite(user.getWebsite());

        return response;
    }
}

