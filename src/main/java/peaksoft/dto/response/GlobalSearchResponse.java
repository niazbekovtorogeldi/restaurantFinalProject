package peaksoft.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record GlobalSearchResponse(

        List<CategoryResponse> categoryResponseList,
        List<SubCategoryResponse> subCategoryResponses,
        List<MenuItemResponse> menuItemResponses
) {
    public GlobalSearchResponse {
    }
}
