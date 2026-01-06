package com.example.mediaPlayer.demo.ServiceImpl;

import com.example.mediaPlayer.demo.Entity.ListeningHistory;
import com.example.mediaPlayer.demo.Entity.Song;
import com.example.mediaPlayer.demo.Entity.User;
import com.example.mediaPlayer.demo.Repo.HistoryRepository;
import com.example.mediaPlayer.demo.Repo.SongRepository;
import com.example.mediaPlayer.demo.Repo.UserRepo;
import com.example.mediaPlayer.demo.ServiceI.HistoryServiceInterface;
import com.example.mediaPlayer.demo.dto.response.ListeningHistoryDTO;
import com.example.mediaPlayer.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryServiceInterface {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private UserRepo userRepository;

    @Override
    public void saveHistory(Long userId, Long songId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException("Song not found"));

        ListeningHistory history = new ListeningHistory();
        history.setUser(user);
        history.setSong(song);
        history.setPlayedAt(LocalDateTime.now());

        historyRepository.save(history);
    }

    @Override
    @Transactional(readOnly = true) // needed for lazy-loading
    public List<ListeningHistoryDTO> getHistory(Long userId) {

        List<ListeningHistory> history =
                historyRepository.findByUserIdOrderByPlayedAtDesc(userId);

        if (history.isEmpty()) {
            throw new ResourceNotFoundException("No listening history found");
        }

        // Map entity -> DTO
        return history.stream()
                .map(h -> new ListeningHistoryDTO(
                        h.getId(),
                        h.getSong().getId(),
                        h.getSong().getTitle(),
                        h.getSong().getArtist().getName(),
                        h.getSong().getAlbum().getName(),
                        h.getSong().getGenre().toString(), // if Genre enum/entity
                        h.getPlayedAt()
                ))
                .toList();
    }
}
