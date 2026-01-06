package com.example.mediaPlayer.demo.Entity;

import com.example.mediaPlayer.demo.util.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean enabled = true;

    @Enumerated(EnumType.STRING)
    private Role role;// ROLE_USER / ROLE_ADMIN

}