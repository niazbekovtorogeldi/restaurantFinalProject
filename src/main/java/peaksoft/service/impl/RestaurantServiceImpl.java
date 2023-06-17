package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.AllRestaurantResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.exceptions.AlreadyExistException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.RestaurantService;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository repository;
    private final UserRepository userRepository;


    @Override
    public SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest) {
        if (!repository.findAll().isEmpty()) {
            throw new AlreadyExistException("You mast save only 1 Restaurant");
        }
        Restaurant restaurant = new Restaurant();
        restaurant = Restaurant.builder()
                .name(restaurantRequest.name())
                .location(restaurantRequest.location())
                .service(restaurantRequest.service())
                .restType(restaurantRequest.restType())
                .countOfEmployees(repository.countOfEmployees(restaurant.getId()))
                .build();
        repository.save(restaurant);
        log.info(String.format("Restaurant with name %s successfully saved !", restaurant.getName()));
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Restaurant with name %s successfully saved !", restaurant.getName()))
                .build();
    }

    @Override
    public AllRestaurantResponse getRestaurant(Long id) {

        return AllRestaurantResponse.builder()
                .restaurantResponse(repository.getRestaurantById(id))
                .countOfEmployees(repository.countOfEmployees(id))
                .build();
    }

    @Override
    public SimpleResponse updateRestaurantById(Long id, RestaurantRequest restaurantRequest) {
        Restaurant restaurant = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Restaurant with id  " + id + "not found !");
                    return new NotFoundException("Restaurant with id  " + id + "not found !");
                });
        restaurant.setName(restaurantRequest.name());
        restaurant.setLocation(restaurantRequest.location());
        restaurant.setService(restaurant.getService());
        repository.save(restaurant);
        log.info(String.format("Restaurant with name %s successfully updated !", restaurant.getName()));
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Restaurant with name %s successfully updated !", restaurant.getName()))
                .build();
    }

    @Override
    public SimpleResponse deleteRestaurant(Long id) {
        Restaurant restaurant = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Restaurant with id  " + id + "not found !");
                    return new NotFoundException("Restaurant with id  " + id + "not found !");
                });
        repository.delete(restaurant);
        List<User> users = restaurant.getUsers();
        users.forEach(user -> user.setRestaurant(null));
        repository.delete(restaurant);
        userRepository.saveAll(users);
        log.info(String.format("Restaurant with name %s successfully deleted !", restaurant.getName()));
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Restaurant with name %s successfully deleted !", restaurant.getName()))
                .build();


    }
}
