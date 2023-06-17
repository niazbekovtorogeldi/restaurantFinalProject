package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.response.page.MenuItemPaginationResponse;
import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.GlobalSearchResponse;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.MenuItemService;

import java.util.List;

@RestController
@RequestMapping("/menuItems")
@RequiredArgsConstructor
public class MenuItemApi {

    private final MenuItemService menuItemService;


    @PostMapping("/{restaurantId}/{subCategories}")
    public SimpleResponse saveMenuItem(@PathVariable Long restaurantId,
                                        @PathVariable Long subCategories,
                                       @RequestBody MenuItemRequest menuItemRequest) {
        return menuItemService.saveMenuItems(restaurantId, subCategories, menuItemRequest);
    }

    @PutMapping("/{id}")
    public SimpleResponse updateMenuItemById(@PathVariable Long id,
                                             @RequestBody MenuItemRequest menuItemRequest) {
        return menuItemService.updateMenuItemsById(id, menuItemRequest);

    }

    @DeleteMapping("/{id}")
    public SimpleResponse deleteMenuItemById(@PathVariable Long id) {
        return menuItemService.deleteMenuItemsById(id);

    }

    @GetMapping("/get/{id}")
    public MenuItemResponse getMenuItemById(@PathVariable Long id) {
        return menuItemService.getMenuItemsById(id);
    }

    @GetMapping("/filter")
    public List<MenuItemResponse> getAllMenuItemsFilterByVegetarian(@RequestParam Boolean isVegetarian) {
        return menuItemService.getAllMenuItemsFilterByVegetarian(isVegetarian);
    }

    @GetMapping("/ascOrDesc")
    public List<MenuItemResponse> getAllMenuItemsOrderByPrice(@RequestParam String ascOrDesc) {
        return menuItemService.getAllMenuItemsOrderByPrice(ascOrDesc);
    }

    @GetMapping("/search")
    public GlobalSearchResponse globalSearchResponse(@RequestParam String word) {
        return menuItemService.globalSearchResponse(word);
    }
    @GetMapping("/{id}")
    public MenuItemPaginationResponse getAllByPagination(@PathVariable Long id,
                                                         @RequestParam int page,
                                                         @RequestParam int size){
        return menuItemService.getPagination(id,page,size);

    }

}
