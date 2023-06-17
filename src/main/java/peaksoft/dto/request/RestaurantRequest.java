package peaksoft.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import peaksoft.enums.RestType;
@Builder
public record RestaurantRequest(
        String name,
        String location,
        @Enumerated(EnumType.STRING)
        RestType restType,
        int service
) {
}
