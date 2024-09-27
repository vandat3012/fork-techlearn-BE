package com.techzen.techlearn.service;

import com.techzen.techlearn.dto.request.UserLoginRequest;
import com.techzen.techlearn.dto.response.AuthenticationResponse;
import com.techzen.techlearn.dto.response.UserResponseDTO;

public interface AuthenticationService {
    AuthenticationResponse login(UserLoginRequest request);

    boolean introspect(String token);
}
