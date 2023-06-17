package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.page.CategoryPaginationResponse;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryApi {

    private final CategoryService categoryService;


    @GetMapping
    public List<CategoryResponse> getAllCategories() {
        return categoryService.getAllCategory();
    }

    @PostMapping
    public SimpleResponse saveCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.saveCategory(categoryRequest);
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PutMapping("/{id}")
    public SimpleResponse updateCategoryById(@PathVariable Long id,
                                             @RequestBody CategoryRequest categoryRequest) {
        return categoryService.updateCategoryById(id, categoryRequest);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse deleteCategoryById(@PathVariable Long id) {
        return categoryService.deleteCategory(id);

    }
    @GetMapping("/page")
    public CategoryPaginationResponse getAllCategoryByPagination(@RequestParam int page,
                                                                 @RequestParam int size){
        return categoryService.getAllCategoryByPagination(page,size);
    }


}
