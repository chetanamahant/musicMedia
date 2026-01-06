package com.example.mediaPlayer.demo.ServiceImpl;

import com.example.mediaPlayer.demo.Entity.Song;
import com.example.mediaPlayer.demo.Entity.SongLike;
import com.example.mediaPlayer.demo.Entity.User;
import com.example.mediaPlayer.demo.Repo.LikeRepository;
import com.example.mediaPlayer.demo.Repo.SongRepository;
import com.example.mediaPlayer.demo.Repo.UserRepo;
import com.example.mediaPlayer.demo.ServiceI.LikeServiceInterface;
import com.example.mediaPlayer.demo.dto.response.SongResponseDTO;
import com.example.mediaPlayer.demo.exception.BadRequestException;
import com.example.mediaPlayer.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LikeServiceImpl implements LikeServiceInterface {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private UserRepo userRepo;

    @Override
    public String likeSong(Long userId, Long songId) {

        if (likeRepository.existsByUserIdAndSongId(userId, songId)) {
            throw new BadRequestException("Song already liked");
        }

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException("Song not found"));

        SongLike like = new SongLike();
        like.setUser(user);
        like.setSong(song);

        likeRepository.save(like);
        return "Song liked";
    }

    @Override
    public void unlikeSong(Long userId, Long songId) {

        SongLike like = likeRepository.findByUserIdAndSongId(userId, songId)
                .orElseThrow(() -> new ResourceNotFoundException("Like not found"));

        likeRepository.delete(like);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SongResponseDTO> getLikedSongs(Long userId) {

        List<SongLike> likes = likeRepository.findByUserIdWithSong(userId);

        return likes.stream()
                .map(like -> new SongResponseDTO(
                        like.getSong().getId(),
                        like.getSong().getTitle(),
                        like.getSong().getGenre().getName(), // convert enum -> String
                        like.getSong().getArtist().getName(),
                        like.getSong().getAlbum().getName()
                ))
                .toList();
    }

}
