package peaksoft.dto.response.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import peaksoft.dto.response.ChequeResponse;

import java.util.List;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChequePaginationResponse {
    private List<ChequeResponse> menuItemResponseList;
    private int page;
    private int size;
}
