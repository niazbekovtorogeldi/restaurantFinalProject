package peaksoft.service;

import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsersFromRestaurant(Long restaurantId);

    SimpleResponse saveUsers(UserRequest userRequest);
    SimpleResponse assignUserToRestaurant(Long restaurantId,Long userId);

    UserResponse getUserById(Long userId);

    SimpleResponse updateUserById(Long userId, UserRequest userRequest);

    SimpleResponse deleteUserById(Long userId);

    boolean existByEmail(String email);
}
