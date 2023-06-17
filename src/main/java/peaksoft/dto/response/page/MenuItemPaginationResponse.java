package peaksoft.dto.response.page;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import peaksoft.dto.response.MenuItemResponse;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemPaginationResponse {
   private List<MenuItemResponse> menuItemResponseList;
    private int page;
    private int size;
}
