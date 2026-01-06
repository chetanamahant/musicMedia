package com.example.mediaPlayer.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListeningHistoryDTO {

    private Long id;
    private Long songId;
    private String title;
    private String artist;
    private String album;
    private String genre;
    private LocalDateTime playedAt;
}
