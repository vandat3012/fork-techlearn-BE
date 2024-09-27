package com.techzen.techlearn.service.impl;

import com.techzen.techlearn.dto.request.UserLoginRequest;
import com.techzen.techlearn.dto.response.AuthenticationResponse;
import com.techzen.techlearn.entity.Token;
import com.techzen.techlearn.entity.UserEntity;
import com.techzen.techlearn.enums.ErrorCode;
import com.techzen.techlearn.enums.TokenType;
import com.techzen.techlearn.exception.AppException;
import com.techzen.techlearn.repository.TokenRepository;
import com.techzen.techlearn.repository.UserRepository;
import com.techzen.techlearn.service.AuthenticationService;
import com.techzen.techlearn.service.JwtService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthenticationServiceImpl implements AuthenticationService {

    UserRepository userRepository;
    JwtService jwtService;
    TokenRepository tokenRepository;
    PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse login(UserLoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new AppException(ErrorCode.INVALID_CREDENTIALS));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        savedUserToken(user, jwtToken);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public boolean introspect(String token) {
        Token existedToken = tokenRepository.findByToken(token)
                .orElse(null);

        if (existedToken == null) {
            return false;
        }

        return !jwtService.isTokenExpired(existedToken.getToken());
    }

    private void savedUserToken(UserEntity user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(UserEntity user) {
        var validTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }

        tokenRepository.deleteAll(validTokens);
    }

}
