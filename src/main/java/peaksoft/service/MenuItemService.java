package peaksoft.service;

import peaksoft.dto.response.page.MenuItemPaginationResponse;
import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.*;

import java.util.List;

public interface MenuItemService {

    List<MenuItemResponse> getAllMenuItemsRestaurantId(Long id);

    SimpleResponse saveMenuItems(Long restaurantId, Long subCategoryId, MenuItemRequest menuItemRequest);

    MenuItemResponse getMenuItemsById(Long id);

    SimpleResponse updateMenuItemsById(Long id, MenuItemRequest menuItemRequest);

    SimpleResponse deleteMenuItemsById(Long id);

    List<MenuItemResponse> getAllMenuItemsFilterByVegetarian(Boolean isVegetarian);

    List<MenuItemResponse> getAllMenuItemsOrderByPrice(String ascOrDesc);

    GlobalSearchResponse globalSearchResponse(String word);

    MenuItemPaginationResponse getPagination(Long id, int page, int size);
}
