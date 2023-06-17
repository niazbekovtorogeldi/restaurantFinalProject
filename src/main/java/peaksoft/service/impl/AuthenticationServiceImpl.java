package peaksoft.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.config.JwtService;
import peaksoft.dto.request.SignInRequest;
import peaksoft.dto.request.SignUpRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.AuthenticationResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exceptions.AlreadyExistException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.UserRepository;
import peaksoft.service.AuthenticationService;

import java.time.ZonedDateTime;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public AuthenticationResponse signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.email())) {
            log.error(String.format("User with email: %s already exists!", signUpRequest.email()));
            throw new AlreadyExistException(String.format(
                    "User with email: %s already exists!", signUpRequest.email()));
        }

        int age = signUpRequest.age();
        int experience = signUpRequest.experience();
        Role role = signUpRequest.role();
        if (role == Role.CHEF && age >= 25 && age <= 45 && experience >= 2) {
            User chef = createUser(signUpRequest);
            return saveUser(chef, "CHEF");
        } else if (role == Role.WAITER && age > 18 && age <= 30 && experience >= 1) {
            User waiter = createUser(signUpRequest);
            return saveUser(waiter, "WAITER");
        } else {
            log.error("Invalid age or experience for the given role!");
            throw new NotFoundException("Invalid age or experience for the given role!");
        }
    }

    private User createUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setFirstName(signUpRequest.firstName());
        user.setLastName(signUpRequest.lastName());
        user.setEmail(signUpRequest.email());
        user.setAge(signUpRequest.age());
        user.setExperience(signUpRequest.experience());
        user.setCreatedDate(ZonedDateTime.now());
        user.setPhoneNumber(signUpRequest.phoneNumber());
        user.setPassword(passwordEncoder.encode(signUpRequest.password()));
        user.setRole(signUpRequest.role());
        return user;

    }

    private AuthenticationResponse saveUser(User user, String roleName) {
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder().
                token(jwtToken).
                email(user.getEmail()).
                role(user.getRole()).
                build();

    }


    @Override
    public AuthenticationResponse signIn(SignInRequest signInRequest) {
        if (signInRequest.email().isBlank()) {
            log.error("Email doesn't exist!");
            throw new BadCredentialsException("Email doesn't exist!");
        }

        User user = userRepository.getUserByEmail(signInRequest.email()).orElseThrow(() -> {
            log.error("User with email: " + signInRequest.email() + " not found");
            return new NotFoundException("User with email: " + signInRequest.email() + " not found");
        });

        if (!passwordEncoder.matches(signInRequest.password(), user.getPassword())) {
            log.error("Incorrect password!");
            throw new BadCredentialsException("Incorrect password!");
        }


        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse
                .builder()
                .email(user.getEmail())
                .role(user.getRole())
                .token(jwtToken)
                .build();
    }

    @PostConstruct
    private void initializeAdmin() {
        User admin = new User();
        admin.setEmail("matmusa@gmail.com");
        admin.setCreatedDate(ZonedDateTime.now());
        admin.setPassword(passwordEncoder.encode("matmusa123"));
        admin.setRole(Role.ADMIN);
        admin.setLastName("tashtanov");
        admin.setFirstName("matmusa");
        admin.setPhoneNumber("+996900909090");
        if (!userRepository.existsByEmail("matmusa@gmail.com")) {
            userRepository.save(admin);
        }
    }
}


