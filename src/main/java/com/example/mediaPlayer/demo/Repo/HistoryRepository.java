package com.example.mediaPlayer.demo.Repo;

import com.example.mediaPlayer.demo.Entity.ListeningHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<ListeningHistory, Long> {
    List<ListeningHistory> findByUserIdOrderByPlayedAtDesc(Long userId);

}

