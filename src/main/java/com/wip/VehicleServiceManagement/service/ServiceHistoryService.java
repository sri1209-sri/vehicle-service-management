package com.wip.VehicleServiceManagement.service;

import com.wip.VehicleServiceManagement.dto.ServiceHistoryDTO;

import java.util.List;
/**
 * ServiceHistoryService.
 *
 * @author Sridevi Srikumar
 */

public interface ServiceHistoryService {

    ServiceHistoryDTO createHistory(ServiceHistoryDTO dto);

    List<ServiceHistoryDTO> getAllHistory();

    ServiceHistoryDTO getHistoryById(Long id);

    ServiceHistoryDTO updateHistory(Long id, ServiceHistoryDTO dto);

    void deleteHistory(Long id);
}