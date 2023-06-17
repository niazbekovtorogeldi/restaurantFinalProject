package peaksoft.dto.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import peaksoft.enums.RestType;
@Builder
@Data
public class RestaurantResponse {


        private Long id;
        private String name;
        private String location;
        @Enumerated(EnumType.STRING)
        private RestType restType;
        private int service;
        public RestaurantResponse(Long id, String name, String location, RestType restType, int service) {
                this.id = id;
                this.name = name;
                this.location = location;
                this.restType = restType;
                this.service = service;
        }



}