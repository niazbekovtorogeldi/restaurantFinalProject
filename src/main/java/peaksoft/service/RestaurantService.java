package peaksoft.service;

import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.AllRestaurantResponse;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;

public interface RestaurantService {


    SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest);

    AllRestaurantResponse getRestaurant(Long id);

    SimpleResponse updateRestaurantById(Long id,RestaurantRequest restaurantRequest);

    SimpleResponse deleteRestaurant(Long id);






















}
