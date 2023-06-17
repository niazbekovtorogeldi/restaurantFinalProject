package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.SubCategoryResponse;
import peaksoft.entity.Subcategory;

import java.util.List;
import java.util.Optional;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

    @Query("select new peaksoft.dto.response.SubCategoryResponse(s.id,s.name)from Subcategory s " +
            " order by s.name asc ")
    List<SubCategoryResponse> getAllSubcategories();

    @Query("select new peaksoft.dto.response.SubCategoryResponse(s.id,s.name)from Subcategory s " +
            "where s.category.id=:id ")
    List<SubCategoryResponse> getAllSubcategoriesByCategory(Long id);

    @Query("select new peaksoft.dto.response.SubCategoryResponse(s.id,s.name)from Subcategory s ")
    Page<SubCategoryResponse> getAllSubcategoriesByPage(Pageable pageable);

    Optional<SubCategoryResponse> getSubcategoriesById(Long id);

}
