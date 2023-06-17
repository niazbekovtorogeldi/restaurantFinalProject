package peaksoft.dto.response.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import peaksoft.dto.response.SubCategoryResponse;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubCategoryPaginationResponse {

    private List<SubCategoryResponse> subCategoryResponseList;

    private int page;
    private int size;


}
