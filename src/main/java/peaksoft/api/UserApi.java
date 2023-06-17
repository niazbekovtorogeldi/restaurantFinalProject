package peaksoft.api;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    @PermitAll
    @GetMapping("/{id}")
    public List<UserResponse> getAllUsers(@PathVariable Long id) {
        return userService.getAllUsersFromRestaurant(id);
    }


    // @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse saveUser(@Valid @RequestBody UserRequest userRequest) {
        return userService.saveUsers(userRequest);
    }


    @PermitAll
    @GetMapping("/get/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }


    // @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public SimpleResponse updateUserById(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        return userService.updateUserById(id, userRequest);
    }

    @DeleteMapping("/{id}")
    private SimpleResponse deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }

    // @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("{restaurantId}/{userId}")
    public SimpleResponse assignUserToCompany(@PathVariable Long restaurantId, @PathVariable Long userId) {
        return userService.assignUserToRestaurant(restaurantId, userId);

    }


}
