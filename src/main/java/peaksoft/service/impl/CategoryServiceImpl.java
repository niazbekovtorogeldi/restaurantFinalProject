package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.page.CategoryPaginationResponse;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.Category;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.CategoryRepository;
import peaksoft.service.CategoryService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> getAllCategory() {
        return categoryRepository.getAllCategory();
    }

    @Override
    public SimpleResponse saveCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.name());
        categoryRepository.save(category);
        log.info(String.format("Category with name %s  successfully saved !", category.getName()));
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Category with name %s  successfully saved !", category.getName()))
                .build();
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        return categoryRepository.getCategoryById(id)
                .orElseThrow(() -> {
                    log.error("User with id " + id + " not found !");
                    return new NotFoundException("User with id " + id + " not found !");
                });

    }

    @Override
    public SimpleResponse updateCategoryById(Long id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User with id " + id + " not found ! ");
                    return new NotFoundException("User with id " + id + " not found ! ");
                });
        category.setName(categoryRequest.name());
        categoryRepository.save(category);
        log.info(String.format("Category with name %s  successfully updated !", category.getName()));
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Category with name %s  successfully updated !", category.getName()))
                .build();
    }

    @Override
    public SimpleResponse deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User with id " + id + " not found ! ");
                    return new NotFoundException("User with id " + id + " not found ! ");
                });
        categoryRepository.delete(category);
        log.info(String.format("Category with name %s  successfully deleted !", category.getName()));
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Category with name %s  successfully deleted !", category.getName()))
                .build();
    }

    @Override
    public CategoryPaginationResponse getAllCategoryByPagination(int page, int size) {
        Pageable pageable= PageRequest.of(page-1,size);
        Page<CategoryResponse>page1=categoryRepository.getAllCategory(pageable);
        return CategoryPaginationResponse.builder()
                .categoryResponseList(page1.getContent())
                .page(page1.getNumber()+1)
                .size(page1.getTotalPages())
                .build();

    }
}
