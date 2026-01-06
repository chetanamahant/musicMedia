package com.example.mediaPlayer.demo.ServiceI;

import com.example.mediaPlayer.demo.Entity.ListeningHistory;
import com.example.mediaPlayer.demo.dto.response.ListeningHistoryDTO;

import java.util.List;

import java.util.List;

public interface HistoryServiceInterface {

    void saveHistory(Long userId, Long songId);

    List<ListeningHistoryDTO> getHistory(Long userId); // return DTO instead of entity
}
