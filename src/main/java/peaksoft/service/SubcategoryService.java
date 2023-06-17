package peaksoft.service;

import peaksoft.dto.request.SubCategoryRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.SubCategoryResponse;
import peaksoft.dto.response.page.SubCategoryPaginationResponse;

import java.util.List;

public interface SubcategoryService {

    List<SubCategoryResponse> getAllSubcategory();

    List<SubCategoryResponse> getAllSubcategoriesByCategory(Long id);

    SimpleResponse saveSubcategory(Long id, SubCategoryRequest subCategoryRequest);

    SubCategoryResponse getSubcategoryById(Long id);

    SimpleResponse updateSubcategoryById(Long id, SubCategoryRequest subCategoryRequest);

    SimpleResponse deleteSubCategoryById(Long id);

    SubCategoryPaginationResponse getAllByPage(int page, int size);

}
