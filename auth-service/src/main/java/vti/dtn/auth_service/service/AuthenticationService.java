package vti.dtn.auth_service.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vti.dtn.auth_service.dto.request.LoginRequest;
import vti.dtn.auth_service.dto.request.RegisterRequest;
import vti.dtn.auth_service.dto.response.LoginResponse;
import vti.dtn.auth_service.dto.response.RegisterResponse;
import vti.dtn.auth_service.dto.response.VerifyTokenResponse;
import vti.dtn.auth_service.entity.UserEntity;
import vti.dtn.auth_service.repo.UserRepository;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository,
                                 JwtService jwtService,
                                 PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    // ==========================================
    // REGISTER USER
    // ==========================================
    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return new RegisterResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "Username already exists!"
            );
        }

        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        userRepository.save(user);

        return new RegisterResponse(
                HttpStatus.CREATED.value(),
                "User registered successfully!"
        );
    }

    // ==========================================
    // LOGIN USER
    // ==========================================
    public LoginResponse login(LoginRequest request) {

        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElse(null);

        if (user == null) {
            return new LoginResponse(
                    HttpStatus.UNAUTHORIZED.value(),
                    "Invalid username!",
                    null, null, null
            );
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new LoginResponse(
                    HttpStatus.UNAUTHORIZED.value(),
                    "Invalid password!",
                    null, null, null
            );
        }

        String access = jwtService.generateAccessToken(user.getUsername());
        String refresh = jwtService.generateRefreshToken(user.getUsername());

        return new LoginResponse(
                HttpStatus.OK.value(),
                "Login successful!",
                user.getId(),
                access,
                refresh
        );
    }

    // ==========================================
    // REFRESH TOKEN
    // ==========================================
    public LoginResponse refreshToken(String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new LoginResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "Invalid token header!",
                    null, null, null
            );
        }

        String token = authHeader.substring(7);

        if (!jwtService.isValid(token)) {
            return new LoginResponse(
                    HttpStatus.UNAUTHORIZED.value(),
                    "Invalid refresh token!",
                    null, null, null
            );
        }

        String username = jwtService.extractUsername(token);

        String newAccess = jwtService.generateAccessToken(username);
        String newRefresh = jwtService.generateRefreshToken(username);

        return new LoginResponse(
                HttpStatus.OK.value(),
                "Token refreshed successfully!",
                null,
                newAccess,
                newRefresh
        );
    }

    // ==========================================
    // VERIFY TOKEN
    // ==========================================
    public VerifyTokenResponse verifyToken(String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new VerifyTokenResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "Invalid authorization header!",
                    null
            );
        }

        String token = authHeader.substring(7);

        if (!jwtService.isValid(token)) {
            return new VerifyTokenResponse(
                    HttpStatus.UNAUTHORIZED.value(),
                    "Invalid token!",
                    null
            );
        }

        return new VerifyTokenResponse(
                HttpStatus.OK.value(),
                "Token valid!",
                token
        );
    }
}
