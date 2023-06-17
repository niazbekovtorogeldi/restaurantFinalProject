package peaksoft.service;

import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.StopListResponse;

import java.util.List;

public interface StopListService {

    List<StopListResponse> getAllStopList();

    SimpleResponse saveStopList(Long menuItemId, StopListRequest stopListRequest);

    StopListResponse getStopListById(Long id);

    SimpleResponse updateStopListById(Long id, StopListRequest stopListRequest);

    SimpleResponse deleteStopListById(Long id);


}
