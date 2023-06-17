package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import
        org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select new peaksoft.dto.response.CategoryResponse(c.id,c.name) from Category  c")
    List<CategoryResponse> getAllCategory();

    @Query("select new peaksoft.dto.response.CategoryResponse(c.id,c.name) from Category  c")
    Page<CategoryResponse> getAllCategory(Pageable pageable);
    Optional<CategoryResponse> getCategoryById(Long id);


}
