package peaksoft.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.SubCategoryResponse;
import peaksoft.entity.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    @Query("select new peaksoft.dto.response.MenuItemResponse" +
            "(m.id,m.name,m.image,m.price,m.description,m.isVegetarian)" +
            " from MenuItem m where m.restaurant.id=:id")
    List<MenuItemResponse> getAllMenuItems(Long id);

    @Query("select new peaksoft.dto.response.MenuItemResponse" +
            "(m.id,m.name,m.image,m.price,m.description,m.isVegetarian)" +
            " from MenuItem m where m.restaurant.id=:id")
    Page<MenuItemResponse> getAllMenuItems(Long id, Pageable pageable);

    @Query("select new peaksoft.dto.response.MenuItemResponse" +
            "(m.id,m.name,m.image,m.price,m.description,m.isVegetarian)" +
            " from MenuItem m where m.isVegetarian=:isVegetarian")
    List<MenuItemResponse> getMenuItemByFilterByVegetarian(@Param("isVegetarian") Boolean isVegetarian);

    @Query("select new peaksoft.dto.response.MenuItemResponse" +
            "(m.id,m.name,m.image,m.price,m.description,m.isVegetarian)" +
            " from MenuItem m order by case when:sortOrder='asc' then m.price end asc ," +
            "case when:sortOrder='desc' then m.price end desc ")
    List<MenuItemResponse> getMenuItemByAscOrDesc(@Param("sortOrder") String sortOrder);

    @Query("select new peaksoft.dto.response.MenuItemResponse" +
            "(m.id,m.name,m.image,m.price,m.description,m.isVegetarian)" +
            " from MenuItem m where m.name like concat('%',:word,'%')")
    List<MenuItemResponse> searchMenuItemWords(@Param("word") String word);
    @Query("select new peaksoft.dto.response.SubCategoryResponse(s.id,s.name)" +
            "from Subcategory s where s.name like concat('%',:word,'%') ")
    List<SubCategoryResponse> searchSubCategoryWords(@Param("word") String word);


    @Query("select new peaksoft.dto.response.CategoryResponse(c.id,c.name)" +
            "from Category  c where c.name like concat('%',:word,'%') ")
    List<CategoryResponse> searchCategoryByWords(@Param("word") String word);


    Optional<MenuItemResponse> getMenuItemById(Long id);

    @Query("select avg (m.price) from MenuItem m where m.id=:id")
    double priceAverage(Long id);


}
