package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.StopListResponse;
import peaksoft.entity.MenuItem;
import peaksoft.entity.StopList;
import peaksoft.exceptions.AlreadyExistException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.StopListRepository;
import peaksoft.service.StopListService;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class StopListServiceImpl implements StopListService {

    private final StopListRepository stopListRepository;
    private final MenuItemRepository menuItemRepository;

    @Override
    public List<StopListResponse> getAllStopList() {
        return stopListRepository.getAllStopList();
    }

    @Override
    public SimpleResponse saveStopList(Long menuItemId, StopListRequest stopListRequest) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> {
                    log.error("MenuItem with id " + menuItemId + " not found !");
                    return new NotFoundException("MenuItem with id " + menuItemId + " not found !");
                });
        if (menuItem.getStopList() != null) {
            log.error("MenuItem with name  " + menuItem.getName() + " already exist !");
            throw new AlreadyExistException("MenuItem with name  " + menuItem.getName() + " already exist!");
        }
        StopList stopList = new StopList();
        stopList.setReason(stopListRequest.reason());
        stopList.setDate(ZonedDateTime.now());
        stopList.setMenuItem(menuItem);
        menuItem.setStopList(stopList);
        stopListRepository.save(stopList);
        log.info(String.format("StopList with id %s  successfully saved !", stopList.getId()));
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("StopList with id %s  successfully saved !", stopList.getId()))
                .build();

    }

    @Override
    public StopListResponse getStopListById(Long id) {
        return stopListRepository.findStopListById(id)
                .orElseThrow(() -> {
                    log.error("StopList with id " + id + " not found !");
                    return new NotFoundException("StopList with id " + id + " not found !");
                });
    }

    @Override
    public SimpleResponse updateStopListById(Long id, StopListRequest stopListRequest) {
        StopList stopList = stopListRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("StopList with id  " + id + "  not found ! ");
                    return new NotFoundException("StopList with id  " + id + "  not found ! ");
                });
        stopList.setReason(stopListRequest.reason());
        stopListRepository.save(stopList);
        log.info(String.format("StopList with id %s  successfully updated !", stopList.getId()));
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("StopList with id %s  successfully updated !", stopList.getId()))
                .build();
    }

    @Override
    public SimpleResponse deleteStopListById(Long id) {
        StopList stopList = stopListRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("StopList with id  " + id + "  not found ! ");
                    return new NotFoundException("StopList with id  " + id + "  not found ! ");
                });
        stopListRepository.delete(stopList);
        log.info(String.format("StopList with id %s  successfully deleted !", stopList.getId()));
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("StopList with id %s  successfully deleted !", stopList.getId()))
                .build();
    }
}
