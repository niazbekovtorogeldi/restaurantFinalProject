package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.StopListResponse;
import peaksoft.entity.StopList;

import java.util.List;
import java.util.Optional;

public interface StopListRepository extends JpaRepository<StopList, Long> {
    @Query("select new peaksoft.dto.response.StopListResponse(s.id,s.reason,s.date)from StopList s ")
    List<StopListResponse> getAllStopList();

    Optional<StopListResponse> findStopListById(Long id);

}
