package peaksoft.service;

import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.AllChequeResponse;
import peaksoft.dto.response.page.ChequePaginationResponse;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;

public interface ChequeService {

    List<ChequeResponse> getAllChequesByUserId(Long id);

    SimpleResponse saveCheque(Long userId, Long menuItemId);

    ChequeResponse getChequesById(Long id);

    SimpleResponse updateChequesById(Long id, ChequeRequest chequeRequest);

    SimpleResponse deleteChequeById(Long id);

    List<AllChequeResponse> getFullInformationFromUser(Long id);
    Double getAllChecksSumsFromRestaurant(Long restaurantId);


    ChequePaginationResponse getPagination(Long id,int page, int size);
}
