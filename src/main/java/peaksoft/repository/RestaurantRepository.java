package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.AllRestaurantResponse;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.entity.Restaurant;
import peaksoft.enums.RestType;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {


    @Query("select new peaksoft.dto.response.RestaurantResponse(r.id,r.name,r.location,r.restType,r.service)from Restaurant  r where r.id=:id")
    List<RestaurantResponse> getRestaurantById(Long id);

    @Query("select count (u) from User u   where u.restaurant.id=:id  ")
    int countOfEmployees(Long id);

    Optional<Restaurant> findById(Long id);
}
