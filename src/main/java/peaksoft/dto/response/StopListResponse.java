package peaksoft.dto.response;

import java.time.ZonedDateTime;

public record StopListResponse(

         Long id,
         String reason,
        ZonedDateTime date
) {
    public StopListResponse {
    }
}
