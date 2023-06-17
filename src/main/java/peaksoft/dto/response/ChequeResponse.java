package peaksoft.dto.response;

import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.ZonedDateTime;

public record ChequeResponse(
        Long id,
        @NonNull
        Double priceAverage,
        ZonedDateTime zonedDateTime
) {
    public ChequeResponse {
    }
}
