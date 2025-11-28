package vti.dtn.auth_service.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vti.dtn.auth_service.dto.request.LoginRequest;
import vti.dtn.auth_service.dto.request.RegisterRequest;
import vti.dtn.auth_service.dto.response.LoginResponse;
import vti.dtn.auth_service.dto.response.RegisterResponse;
import vti.dtn.auth_service.dto.response.VerifyTokenResponse;
import vti.dtn.auth_service.entity.UserEntity;
import vti.dtn.auth_service.repo.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // ==========================================================
    // REGISTER
    // ==========================================================
    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return new RegisterResponse(400, "Username already exists");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        UserEntity user = new UserEntity(
                request.getUsername(),
                encodedPassword,
                request.getEmail(),
                request.getRole(),
                request.getFirstName(),
                request.getLastName()
        );

        userRepository.save(user);

        return new RegisterResponse(200, "Register successfully");
    }

    // ==========================================================
    // LOGIN
    // ==========================================================
    public LoginResponse login(LoginRequest request) {

        Optional<UserEntity> optionalUser =
                userRepository.findByUsername(request.getUsername());

        if (optionalUser.isEmpty()) {
            return new LoginResponse(
                    401,
                    "User not found",
                    null,
                    null,
                    null
            );
        }

        UserEntity user = optionalUser.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new LoginResponse(
                    401,
                    "Invalid password",
                    null,
                    null,
                    null
            );
        }

        // Generate tokens (STRING username)
        String accessToken = jwtService.generateAccessToken(user.getUsername());
        String refreshToken = jwtService.generateRefreshToken(user.getUsername());

        return new LoginResponse(
                200,
                "Login successful",
                user.getId(),
                accessToken,
                refreshToken
        );
    }

    // ==========================================================
    // VERIFY TOKEN
    // ==========================================================
    public VerifyTokenResponse verifyToken(String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new VerifyTokenResponse(
                    400,
                    "Invalid token format",
                    null
            );
        }

        String token = authHeader.substring(7);

        boolean valid = jwtService.isValid(token);

        if (!valid) {
            return new VerifyTokenResponse(
                    401,
                    "Token invalid",
                    null
            );
        }

        String username = jwtService.extractUsername(token);

        return new VerifyTokenResponse(
                200,
                "Token valid",
                username
        );
        public UserEntity findByUsername(String username) {
            return userRepository.findByUsername(username).orElse(null);
        }

    }
}
