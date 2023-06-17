package peaksoft.dto.response.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import peaksoft.dto.response.CategoryResponse;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryPaginationResponse {
    private List<CategoryResponse> categoryResponseList;
    private int page;
    private int size;


}
