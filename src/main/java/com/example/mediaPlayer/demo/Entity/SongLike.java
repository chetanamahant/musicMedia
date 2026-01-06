package com.example.mediaPlayer.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "song_id"}),
        indexes = {
                @Index(name = "idx_song_like_user", columnList = "user_id"),
                @Index(name = "idx_song_like_song", columnList = "song_id")
        }
)
public class SongLike {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        private User user;

        @ManyToOne(fetch = FetchType.LAZY)
        private Song song;
}

