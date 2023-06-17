package peaksoft.dto.request;

import jakarta.persistence.Lob;
import lombok.Builder;

import java.util.List;
@Builder
public record MenuItemRequest(
        String name,
        @Lob
        List<String> image,
        int price,
        String description,
        Boolean isVegetarian

) {
}
