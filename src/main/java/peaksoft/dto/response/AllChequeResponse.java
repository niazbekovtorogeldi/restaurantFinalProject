package peaksoft.dto.response;

import lombok.Builder;
import peaksoft.entity.MenuItem;

import java.util.List;
@Builder
public record AllChequeResponse(

        String WaitersFullName,
        List<MenuItemResponse> menuItems,
        Double averagePrice,
        Double service,
        Double grandTotal
        ) {

}
