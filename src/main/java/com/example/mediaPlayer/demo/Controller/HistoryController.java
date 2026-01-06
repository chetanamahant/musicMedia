package com.example.mediaPlayer.demo.Controller;
import com.example.mediaPlayer.demo.Entity.Album;
import com.example.mediaPlayer.demo.Entity.ListeningHistory;
import com.example.mediaPlayer.demo.ServiceI.AlbumServiceInterface;
import com.example.mediaPlayer.demo.ServiceI.HistoryServiceInterface;
import com.example.mediaPlayer.demo.dto.response.ApiSuccessResponse;
import com.example.mediaPlayer.demo.dto.response.ListeningHistoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryServiceInterface historyService;

    @PostMapping("/saveHistory")
    public ResponseEntity<ApiSuccessResponse<String>> saveHistory(
            @RequestParam Long userId,
            @RequestParam Long songId) {

        historyService.saveHistory(userId, songId);

        return ResponseEntity.ok(
                new ApiSuccessResponse<>(
                        true,
                        "Listening history saved",
                        null,
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/getHistoryByUserId/{userId}")
    public ResponseEntity<ApiSuccessResponse<List<ListeningHistoryDTO>>> getHistory(
            @PathVariable Long userId) {

        List<ListeningHistoryDTO> history = historyService.getHistory(userId);

        return ResponseEntity.ok(
                new ApiSuccessResponse<>(
                        true,
                        "Listening history fetched",
                        history,
                        LocalDateTime.now()
                )
        );
    }
}
