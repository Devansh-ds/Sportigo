package com.sadds.security_controller;

import com.sadds.config.JwtService;
import com.sadds.exception.TokenInvalidException;
import com.sadds.exception.UserAlreadyExistException;
import com.sadds.exception.UserException;
import com.sadds.model.User;
import com.sadds.repo.UserRepository;
import com.sadds.model.Token;
import com.sadds.repo.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 AuthenticationManager authenticationManager,
                                 TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;

    }

    public AuthenticationResponse register(RegisterRequest request) throws UserAlreadyExistException {

        var user = User.builder()
                .fullname(request.getFullname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        var refreshToken = jwtService.generateRefreshToken(user);

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("Email already in use: " + request.getEmail());
        }

        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        saveUserToken(savedUser, jwtToken);

        return new AuthenticationResponse(jwtToken, refreshToken);
    }

    private void revokeAllUserTokens(User user) {
        var validToken = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validToken.isEmpty()) {
            return;
        }
        validToken.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validToken);
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) throws UserException {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserException(request.getEmail() + " does not exist"));
        var jwtToken = jwtService.generateToken(user);

        var refreshToken = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return new AuthenticationResponse(jwtToken, refreshToken);
    }

    private void saveUserToken(User savedUser, String jwtToken) {
        var token = new Token(
                jwtToken,
                false,
                false,
                savedUser
        );
        tokenRepository.save(token);
    }

    public AuthenticationResponse refreshToken(String refreshToken) throws IOException, TokenInvalidException {
        String userEmail;

        if (refreshToken == null || !refreshToken.startsWith("Bearer ")) {
             throw new TokenInvalidException("token might be null or empty");
        }
        refreshToken = refreshToken.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        System.out.println(userEmail);
        System.out.println(SecurityContextHolder.getContext().getAuthentication());

        if (userEmail != null) {
            var user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new UsernameNotFoundException(userEmail));

            if (jwtService.validateToken(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
//                var authResponse = new AuthenticationResponse(accessToken, refreshToken);
//                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
                return new AuthenticationResponse(accessToken, refreshToken);
            } else {
                throw new TokenInvalidException("refresh token is invalid");
            }
        } else {
            throw new TokenInvalidException("Weird refresh token");
        }

    }
}













