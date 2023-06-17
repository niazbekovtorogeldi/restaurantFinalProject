package peaksoft.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.AllRestaurantResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.RestaurantService;


@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantApi {
    private final RestaurantService restaurantService;

    //  @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse saveRestaurant(@RequestBody RestaurantRequest restaurantRequest) {
        return restaurantService.saveRestaurant(restaurantRequest);
    }


    @GetMapping("/{id}")
    public AllRestaurantResponse getRestaurantById(@PathVariable Long id) {
        return restaurantService.getRestaurant(id);
    }

  //  @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public SimpleResponse updateRestaurant(@PathVariable Long id, @RequestBody RestaurantRequest restaurantRequest) {
        return restaurantService.updateRestaurantById(id, restaurantRequest);
    }

   // @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteRestaurantById(@PathVariable Long id) {
        return restaurantService.deleteRestaurant(id);
    }




}
