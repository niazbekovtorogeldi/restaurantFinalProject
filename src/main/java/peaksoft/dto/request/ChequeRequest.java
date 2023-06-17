package peaksoft.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class ChequeRequest {
    @NonNull
    private  int priceAverage;

    public ChequeRequest(int priceAverage) {
        this.priceAverage = priceAverage;
    }
}
