package peaksoft.api;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.SubCategoryRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.SubCategoryResponse;
import peaksoft.dto.response.page.SubCategoryPaginationResponse;
import peaksoft.service.SubcategoryService;

import java.util.List;

@RestController
@RequestMapping("/subcategories")
@RequiredArgsConstructor
public class SubCategoryApi {
    private final SubcategoryService service;


    @GetMapping("/{id}")
    public List<SubCategoryResponse> getAllSubCategoryResponsesByCategoryId(@PathVariable Long id) {
        return service.getAllSubcategoriesByCategory(id);
    }

    @GetMapping
    public List<SubCategoryResponse> getAllSubCategories() {
        return service.getAllSubcategory();
    }

    @PostMapping("/{id}")
    public SimpleResponse saveSubCategory(@PathVariable Long id, @RequestBody SubCategoryRequest subCategoryRequest) {
        return service.saveSubcategory(id, subCategoryRequest);
    }

    @GetMapping("/get/{id}")
    public SubCategoryResponse getSubCategoryById(@PathVariable Long id) {
        return service.getSubcategoryById(id);
    }

    @PutMapping("/{id}")
    public SimpleResponse updateCategoryById(@PathVariable Long id,
                                             @RequestBody SubCategoryRequest subCategoryRequest) {
        return service.updateSubcategoryById(id, subCategoryRequest);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse deleteCategoryById(@PathVariable Long id) {
        return service.deleteSubCategoryById(id);
    }


    @GetMapping("/page")
    public SubCategoryPaginationResponse subCategoryPaginationResponse(
            @RequestParam int page,
            @RequestParam int size) {
        return service.getAllByPage( page, size);
    }


}
