package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.*;
import peaksoft.dto.response.page.ChequePaginationResponse;
import peaksoft.entity.Cheque;
import peaksoft.entity.MenuItem;
import peaksoft.entity.User;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.ChequeRepository;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.ChequeService;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ChequeServiceImpl implements ChequeService {
    private final ChequeRepository chequeRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;


    @Override
    public List<ChequeResponse> getAllChequesByUserId(Long id) {
        return chequeRepository.getAllChequesByUserId(id);
    }

    @Override
    public SimpleResponse saveCheque(Long userId, Long menuItemId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.error("User with id " + userId + " doesn't exist");
            return new NotFoundException("User with id " + userId + " doesn't exist");
        });
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() -> {
            log.error("User with id " + userId + " doesn't exist");
            return new NotFoundException("User with id " + userId + " doesn't exist");
        });

        List<MenuItem> menuItems = new ArrayList<>();
        List<Cheque> cheques = new ArrayList<>();
        menuItems.add(menuItem);

        Cheque cheque = new Cheque();
        cheque.setPriceAverage(menuItemRepository.priceAverage(menuItem.getId()));
        cheque.setMenuItems(menuItems);
        cheque.setUser(user);
        cheque.setZonedDateTime(ZonedDateTime.now());
        cheques.add(cheque);
        menuItem.setCheques(cheques);

        chequeRepository.save(cheque);
        userRepository.save(user);
        menuItemRepository.save(menuItem);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Cheque with id %s successfully saved!", cheque.getId()))
                .build();

    }

    @Override
    public ChequeResponse getChequesById(Long id) {
        return chequeRepository.getChequeById(id)
                .orElseThrow(() -> {
                    log.error("Cheque with id  " + id + "  not found !");
                    return new NotFoundException("Cheque with id  " + id + "  not found !");
                });
    }

    @Override
    public SimpleResponse updateChequesById(Long id, ChequeRequest chequeRequest) {
        Cheque cheque = chequeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Cheque with id  " + id + "  not found !");
                    return new NotFoundException("Cheque with id  " + id + "  not found !");
                });
        cheque.setPriceAverage(chequeRepository.averagePrice(cheque.getId()));
        log.info(String.format("Cheque with id %s successfully updated !", cheque.getId()));
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Cheque with id %s successfully updated !", cheque.getId()))
                .build();
    }


    @Override
    public SimpleResponse deleteChequeById(Long id) {
        Cheque cheque = chequeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Cheque with id  " + id + "  not found !");
                    return new NotFoundException("Cheque with id  " + id + "  not found !");
                });
        chequeRepository.delete(cheque);
        log.info(String.format("Cheque with id %s successfully deleted !", cheque.getId()));
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Cheque with id %s successfully deleted !", cheque.getId()))
                .build();
    }

    @Override
    public List<AllChequeResponse> getFullInformationFromUser(Long id) {
        Double service = chequeRepository.grandTotal(id) * 12 / 100;
        List<AllChequeResponse> allChequeResponses = new ArrayList<>();
        AllChequeResponse allChequeResponse = AllChequeResponse.builder()
                .WaitersFullName(chequeRepository.userFullName(id))
                .menuItems(chequeRepository.getAllMenuItems(id))
                .averagePrice(chequeRepository.averagePrice(id))
                .service(service)
                .grandTotal(chequeRepository.grandTotal(id))
                .build();
        allChequeResponses.add(allChequeResponse);
        return allChequeResponses;
    }

    @Override
    public Double getAllChecksSumsFromRestaurant(Long restaurantId) {
        return chequeRepository.getAllChecksSumsFromRestaurant(restaurantId);
    }

    @Override
    public ChequePaginationResponse getPagination(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<ChequeResponse>chequeResponses = chequeRepository.getAllMenuItems(id,pageable);
        return ChequePaginationResponse.builder()
                .menuItemResponseList(chequeResponses.getContent())
                .page(chequeResponses.getNumber()+1)
                .size(chequeResponses.getTotalPages())
                .build();
    }

}
