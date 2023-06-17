package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.dto.request.SubCategoryRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.SubCategoryResponse;
import peaksoft.dto.response.page.SubCategoryPaginationResponse;
import peaksoft.entity.Category;
import peaksoft.entity.Subcategory;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.CategoryRepository;
import peaksoft.repository.SubcategoryRepository;
import peaksoft.service.SubcategoryService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SubcategoryServiceImpl implements SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<SubCategoryResponse> getAllSubcategory() {
        return subcategoryRepository.getAllSubcategories();
    }

    @Override
    public List<SubCategoryResponse> getAllSubcategoriesByCategory(Long id) {
        return subcategoryRepository.getAllSubcategoriesByCategory(id);
    }

    @Override
    public SimpleResponse saveSubcategory(Long id, SubCategoryRequest subCategoryRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("Category with id " + id + " not found");
                    return new NotFoundException("Category with id " + id + " not found");
                });
        Subcategory subcategory = new Subcategory();
        subcategory.setName(subCategoryRequest.name());
        List<Subcategory> subcategories = new ArrayList<>();
        subcategories.add(subcategory);
        category.setSubcategory(subcategories);
        subcategoryRepository.save(subcategory);
        categoryRepository.save(category);
        log.info(String.format("Subcategory with name %s  successfully saved", subcategory.getName()));
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Subcategory with name %s  successfully saved", subcategory.getName()))
                .build();
    }

    @Override
    public SubCategoryResponse getSubcategoryById(Long id) {
        return subcategoryRepository.getSubcategoriesById(id)
                .orElseThrow(() -> {
                    log.error("SubCategory with id  " + id + "  doesn't exist");
                    return new NotFoundException("SubCategory with id  " + id + "  doesn't exist");
                });
    }

    @Override
    public SimpleResponse updateSubcategoryById(Long id, SubCategoryRequest subCategoryRequest) {
        Subcategory subcategory = subcategoryRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Subcategory with id  " + id + "  not found !");
                    return new NotFoundException("Subcategory with id  " + id + "  not found !");
                });
        subcategory.setName(subCategoryRequest.name());
        subcategoryRepository.save(subcategory);
        log.info(String.format("Subcategory with name %s successfully updated !", subcategory.getName()));
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Subcategory with name %s successfully updated !", subcategory.getName()))
                .build();
    }

    @Override
    public SimpleResponse deleteSubCategoryById(Long id) {
        Subcategory subcategory = subcategoryRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Subcategory with id  " + id + "  not found !");
                    return new NotFoundException("Subcategory with id  " + id + "  not found !");
                });
        subcategoryRepository.delete(subcategory);
        log.info(String.format("Subcategory with name %s successfully deleted !", subcategory.getName()));
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Subcategory with name %s successfully deleted !", subcategory.getName()))
                .build();
    }

    @Override
    public SubCategoryPaginationResponse getAllByPage( int page, int size) {
        Pageable pageable = PageRequest.of(page-1,size);
        Page<SubCategoryResponse> subCategoryResponsePage = subcategoryRepository.getAllSubcategoriesByPage(pageable);

        return SubCategoryPaginationResponse.builder()
                .subCategoryResponseList(subCategoryResponsePage.getContent())
                .page(subCategoryResponsePage.getNumber()+1)
                .size(subCategoryResponsePage.getTotalPages())
                .build();
    }
}
