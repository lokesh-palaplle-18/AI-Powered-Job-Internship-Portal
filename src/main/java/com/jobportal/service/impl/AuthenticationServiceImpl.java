
package com.jobportal.service.impl;

import com.jobportal.dto.request.LoginRequest;
import com.jobportal.dto.response.LoginResponse;
import com.jobportal.entity.User;
import com.jobportal.repository.UserRepository;
import com.jobportal.security.JwtService;
import com.jobportal.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl
        implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Value("${jwt.expiration}")
    private long expiration;

    @Override
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new org.springframework.security.core.userdetails
                                .UsernameNotFoundException(
                                "User not found"
                        )
                );

        String token = jwtService.generateToken(user);

        LoginResponse response = new LoginResponse();

        response.setAccessToken(token);
        response.setTokenType("Bearer");
        response.setExpiresIn(expiration);
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());

        return response;
    }
}

