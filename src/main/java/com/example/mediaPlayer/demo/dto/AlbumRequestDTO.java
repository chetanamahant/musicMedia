package com.example.mediaPlayer.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumRequestDTO {
    private String name;
    private Long artistId;
}
