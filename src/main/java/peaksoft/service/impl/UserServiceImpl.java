package peaksoft.service.impl;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.UserService;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;

    @Override
    public List<UserResponse> getAllUsersFromRestaurant(Long restaurantId) {
        return userRepository.getAllUser(restaurantId);
    }

    @Override
    public SimpleResponse saveUsers(UserRequest userRequest) {
        int age = userRequest.age();
        int experience = userRequest.experience();
        Role role = userRequest.role();
        if (role == Role.CHEF && age > 25 && age <= 45 && experience >= 2) {
            User chef = createUser(userRequest);
            return saveUser(chef, "CHEF");
        } else if (role == Role.WAITER && age > 18 && age <= 30 && experience >= 1) {
            User waiter = createUser(userRequest);
            return saveUser(waiter, "WAITER");
        } else {
            log.error("Invalid age or experience for the given role!");
            throw new BadRequestException("Invalid age or experience for the given role!");
        }
    }

    private User createUser(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setEmail(userRequest.email());
        user.setAge(userRequest.age());
        user.setPhoneNumber(userRequest.phoneNumber());
        user.setExperience(userRequest.experience());
        user.setCreatedDate(ZonedDateTime.now());
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setRole(userRequest.role());
        return user;

    }

    private SimpleResponse saveUser(User user, String roleName) {
        userRepository.save(user);
        log.info(String.format("%s with name %s successfully saved !", roleName, user.getFirstName()));
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("%s with name %s successfully saved !", roleName, user.getFirstName()))
                .build();

    }

    @Override
    public SimpleResponse assignUserToRestaurant(Long restaurantId, Long userId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> {
                    log.error("Restaurant with id  " + restaurantId + " not found !");
                    return new NotFoundException("Restaurant with id  " + restaurantId + " not found !");
                });
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.error("User with id  " + userId + " not  found !");
            return new NotFoundException("User with id  " + userId + " not  found !");
        });
        if (restaurant.getCountOfEmployees() <= 15) {
            List<User> users = new ArrayList<>();
            users.add(user);
            restaurant.setUsers(users);
            user.setRestaurant(restaurant);
            restaurantRepository.save(restaurant);
            userRepository.save(user);
            log.info(String.format("User with id %s successfully added restaurant with id %s !", userId, restaurantId));
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message(String.format("User with id %s successfully added restaurant with id %s !", userId, restaurantId))
                    .build();

        } else {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.CONFLICT)
                    .message("Restaurant user count didn't more 15 !")
                    .build();
        }
    }

    @Override
    public UserResponse getUserById(Long userId) {
        return userRepository.getUserById(userId).
                orElseThrow(() -> {
                    log.error("User with id" + userId + " not found ! ");
                    return new NotFoundException("User with id" + userId + " not found ! ");
                });
    }

    @Override
    public SimpleResponse updateUserById(Long userId, UserRequest userRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User with id " + userId + " not found !");
                    return new NotFoundException("User with id " + userId + " not found !");
                });
        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.firstName());
        user.setEmail(userRequest.email());
        user.setPassword(userRequest.password());
        user.setAge(user.getAge());
        user.setPhoneNumber(userRequest.phoneNumber());
        user.setExperience(userRequest.experience());
        user.setCreatedDate(ZonedDateTime.now());
        userRepository.save(user);
        log.info(String.format("User with name %s successfully updated !", user.getFirstName()));
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("User with name %s successfully updated !", user.getFirstName()))
                .build();
    }

    @Override
    public SimpleResponse deleteUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User with id " + userId + " not found !");
                    return new NotFoundException("User with id " + userId + " not found !");
                });
        userRepository.delete(user);
        log.info(String.format("User with name %s successfully deleted !", user.getFirstName()));
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("User with name %s successfully deleted !", user.getFirstName()))
                .build();
    }

    @Override
    public boolean existByEmail(String email) {
        String queryString = "SELECT COUNT(u) FROM User u WHERE u.email = :email";
        Long count = entityManager.createQuery(queryString, Long.class)
                .setParameter("email", email)
                .getSingleResult();
        return count > 0;
    }
}
