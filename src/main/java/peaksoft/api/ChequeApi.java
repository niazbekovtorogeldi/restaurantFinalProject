package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.response.AllChequeResponse;
import peaksoft.dto.response.page.ChequePaginationResponse;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.ChequeService;

import java.util.List;

@RestController
@RequestMapping("/cheques")
@RequiredArgsConstructor
public class ChequeApi {

    private final ChequeService chequeService;

    @GetMapping("/{id}")
    public List<ChequeResponse> getAllCheques(@PathVariable Long id) {
        return chequeService.getAllChequesByUserId(id);
    }

    @PostMapping("/{id}/{menuItemsId}")
    public SimpleResponse saveCheque(@PathVariable Long id, @PathVariable Long menuItemsId) {
        return chequeService.saveCheque(id, menuItemsId);
    }

    @GetMapping("/get/{id}")
    public ChequeResponse getChequeById(@PathVariable Long id) {
        return chequeService.getChequesById(id);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse deleteChequeById(@PathVariable Long id) {
        return chequeService.deleteChequeById(id);
    }

    @GetMapping("all/{id}")
    List<AllChequeResponse> getAllInformationFromUser(@PathVariable Long id) {
        return chequeService.getFullInformationFromUser(id);
    }

    @GetMapping("/sum/{restaurantId}")
    public Double getAllChecksSumsFromRestaurant(@PathVariable Long restaurantId) {
        return chequeService.getAllChecksSumsFromRestaurant(restaurantId);

    }

    @GetMapping("/page/{id}")
    public ChequePaginationResponse getAllByPagination(@PathVariable Long id,
                                                 @RequestParam int page,
                                                 @RequestParam int size){
        return chequeService.getPagination(id,page,size);

    }
}
