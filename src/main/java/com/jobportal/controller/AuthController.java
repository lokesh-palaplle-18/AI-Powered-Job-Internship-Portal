
package com.jobportal.controller;

import com.jobportal.dto.request.LoginRequest;
import com.jobportal.dto.response.LoginResponse;
import com.jobportal.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {

        LoginResponse response =
                authenticationService.login(request);

        return ResponseEntity.ok(response);
    }
}
