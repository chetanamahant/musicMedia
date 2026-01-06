package com.example.mediaPlayer.demo.ServiceI;

import com.example.mediaPlayer.demo.Entity.Song;
import com.example.mediaPlayer.demo.Entity.SongLike;
import com.example.mediaPlayer.demo.dto.response.SongResponseDTO;

import java.util.List;

public interface LikeServiceInterface {


    String likeSong(Long userId, Long songId);

    void unlikeSong(Long userId, Long songId);

    List<SongResponseDTO> getLikedSongs(Long userId);

}
