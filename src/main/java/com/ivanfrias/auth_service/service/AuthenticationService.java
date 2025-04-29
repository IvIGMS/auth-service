package com.ivanfrias.auth_service.service;

import com.ivanfrias.auth_service.dto.AuthenticationRequest;
import com.ivanfrias.auth_service.dto.AuthenticationResponse;
import com.ivanfrias.auth_service.dto.RegisterRequest;
import com.ivanfrias.auth_service.model.UserEntity;
import com.ivanfrias.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = UserEntity.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .firstname(request.firstname())
                .lastname(request.lastname())
                .isActive(true)
                .storeId(request.storeId())
                .build();
        userRepository.save(user);

        var userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("USER")
                .accountLocked(!user.getIsActive())
                .build();

        var jwtToken = jwtService.generateToken(userDetails);
        return new AuthenticationResponse(jwtToken);
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        var userDetails = (UserDetails) authentication.getPrincipal();
        var jwtToken = jwtService.generateToken(userDetails);
        return new AuthenticationResponse(jwtToken);
    }
}

