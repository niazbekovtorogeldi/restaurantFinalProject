package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.dto.response.page.MenuItemPaginationResponse;
import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.GlobalSearchResponse;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Restaurant;
import peaksoft.entity.Subcategory;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.SubcategoryRepository;
import peaksoft.service.MenuItemService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final SubcategoryRepository subcategoryRepository;

    @Override
    public List<MenuItemResponse> getAllMenuItemsRestaurantId(Long id) {
        return menuItemRepository.getAllMenuItems(id);

    }

    @Override
    public SimpleResponse saveMenuItems(Long restaurantId, Long subCategoryId, MenuItemRequest menuItemRequest) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> {
                    log.info(String.format("Restaurant with id %s not found !", restaurantId));
                    return new NotFoundException
                            (String.format("Restaurant with id %s not found !", restaurantId));
                });


        Subcategory subcategory = subcategoryRepository.findById(restaurantId)
                .orElseThrow(() -> {
                    log.error(String.format("Subcategory with id %s not found !", subCategoryId));
                    return new NotFoundException
                            (String.format("Subcategory with id %s not found !", subCategoryId));
                });
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemRequest.name());
        menuItem.setImage(menuItemRequest.image());
        menuItem.setPrice(menuItemRequest.price());
        menuItem.setDescription(menuItemRequest.description());
        menuItem.setIsVegetarian(menuItemRequest.isVegetarian());
        List<MenuItem> menuItems = new ArrayList<>();
        restaurant.setMenuItems(menuItems);
        menuItem.setRestaurant(restaurant);
        subcategory.setMenuItems(menuItems);
        menuItem.setSubcategory(subcategory);

        menuItemRepository.save(menuItem);
        restaurantRepository.save(restaurant);
        subcategoryRepository.save(subcategory);
        log.info(String.format("MenuItem with name %s  successfully saved ! ", menuItem.getName()));
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("MenuItem with name %s  successfully saved ! ", menuItem.getName()))
                .build();
    }


    @Override
    public MenuItemResponse getMenuItemsById(Long id) {
        return menuItemRepository.getMenuItemById(id)
                .orElseThrow(() -> {
                    log.error("MenuItem with id  " + id + "  not found !");
                    return new NotFoundException("MenuItem with id  " + id + "  not found !");
                });
    }

    @Override
    public SimpleResponse updateMenuItemsById(Long id, MenuItemRequest menuItemRequest) {
        MenuItem menuItem = menuItemRepository.
                findById(id).orElseThrow(() -> {
                    log.error("MenuItem with id  " + id + "  not found !");
                    return new NotFoundException("MenuItem with id  " + id + "  not found !");
                });
        menuItem.setName(menuItemRequest.name());
        menuItem.setImage(menuItemRequest.image());
        menuItem.setPrice(menuItemRequest.price());
        menuItem.setDescription(menuItemRequest.description());
        menuItem.setIsVegetarian(menuItemRequest.isVegetarian());
        menuItemRepository.save(menuItem);
        log.info(String.format("MenuItem with name %s  successfully updated ! ", menuItem.getName()));
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("MenuItem with name %s  successfully updated ! ", menuItem.getName()))
                .build();

    }

    @Override
    public SimpleResponse deleteMenuItemsById(Long id) {
        MenuItem menuItem = menuItemRepository.
                findById(id).orElseThrow(() -> {
                    log.error("MenuItem with id  " + id + "  not found !");
                    return new NotFoundException("MenuItem with id  " + id + "  not found !");
                });
        menuItemRepository.delete(menuItem);
        log.info(String.format("MenuItem with name %s successfully deleted ! ", menuItem.getName()));
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("MenuItem with name %s successfully deleted ! ", menuItem.getName()))
                .build();
    }

    @Override
    public List<MenuItemResponse> getAllMenuItemsFilterByVegetarian(Boolean isVegetarian) {
        return menuItemRepository.getMenuItemByFilterByVegetarian(isVegetarian);
    }

    @Override
    public List<MenuItemResponse> getAllMenuItemsOrderByPrice(String ascOrDesc) {
        return menuItemRepository.getMenuItemByAscOrDesc(ascOrDesc);
    }

    @Override
    public GlobalSearchResponse globalSearchResponse(String word) {
        return GlobalSearchResponse.builder()
                .menuItemResponses(menuItemRepository.searchMenuItemWords(word))
                .categoryResponseList(menuItemRepository.searchCategoryByWords(word))
                .subCategoryResponses(menuItemRepository.searchSubCategoryWords(word))
                .build();
    }

    @Override
    public MenuItemPaginationResponse getPagination(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("name"));
        Page<MenuItemResponse> menuItemResponses = menuItemRepository.getAllMenuItems(id, pageable);
        return MenuItemPaginationResponse.builder()
                .menuItemResponseList(menuItemResponses.getContent())
                .page(menuItemResponses.getNumber()+1)
                .size(menuItemResponses.getTotalPages())
                .build();
    }
}
