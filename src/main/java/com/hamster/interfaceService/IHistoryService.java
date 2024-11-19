package com.hamster.interfaceService;

import java.util.List;
import java.util.Optional;

import com.hamster.dto.HistoryDTO;
import com.hamster.model.Histories;

public interface IHistoryService {
	List<Histories> getAllHistories();
    Optional<Histories> getHistoryById(Long id);
    Histories createHistory(Histories history);
    Histories updateHistory(Long id, Histories history);
    void deleteHistory(Long id);
    HistoryDTO convertToDTO(Histories history);
    Histories convertToEntity(HistoryDTO historyDTO);
}
