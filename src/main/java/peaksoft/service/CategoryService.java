package peaksoft.service;

import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.page.CategoryPaginationResponse;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;

public interface CategoryService {


    List<CategoryResponse> getAllCategory();

    SimpleResponse saveCategory(CategoryRequest categoryRequest);

    CategoryResponse getCategoryById(Long id);

    SimpleResponse updateCategoryById(Long id, CategoryRequest categoryRequest);

    SimpleResponse deleteCategory(Long id);

    CategoryPaginationResponse getAllCategoryByPagination(int page,int size);

}
