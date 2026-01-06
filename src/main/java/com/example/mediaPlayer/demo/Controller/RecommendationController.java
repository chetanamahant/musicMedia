package com.example.mediaPlayer.demo.Controller;

import com.example.mediaPlayer.demo.Entity.Song;
import com.example.mediaPlayer.demo.ServiceI.RecommendationServiceInterface;
import com.example.mediaPlayer.demo.dto.response.ApiSuccessResponse;
import com.example.mediaPlayer.demo.dto.response.SongResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    @Autowired
    private RecommendationServiceInterface recommendationService;

    @GetMapping("/recommendSongs")
    public ResponseEntity<ApiSuccessResponse<List<SongResponseDTO>>> recommendSongs() {

        return ResponseEntity.ok(
                new ApiSuccessResponse<>(
                        true,
                        "Recommended songs fetched successfully",
                        recommendationService.recommendSongs(),
                        LocalDateTime.now()
                )
        );
    }
}





