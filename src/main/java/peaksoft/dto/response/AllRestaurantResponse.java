package peaksoft.dto.response;

import jakarta.validation.constraints.Max;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Builder
@Data
public class AllRestaurantResponse {
    private List<RestaurantResponse> restaurantResponse;
    @Max(value = 15, message = "there is no vacancy!")
    private int countOfEmployees;

    public AllRestaurantResponse(List<RestaurantResponse> restaurantResponse, int countOfEmployees) {
        this.restaurantResponse = restaurantResponse;
        this.countOfEmployees = countOfEmployees;
    }
}

