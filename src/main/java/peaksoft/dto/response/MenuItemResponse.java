package peaksoft.dto.response;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.Min;
import lombok.Builder;

import java.util.List;
@Builder
public record MenuItemResponse(
         Long id,
         String name,
         @Lob
         List<String>image,
         @Min(value = 1, message = "Price must be greater than zero")
         int price,
         String description,
         Boolean isVegetarian
) {
    public MenuItemResponse {
    }
}
