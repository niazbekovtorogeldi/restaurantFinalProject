package peaksoft.dto.request;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record SignInRequest(
        @NonNull
        String email,
        @NonNull
        String password
) {
}
