package com.example.mediaPlayer.demo.configuration;

import com.example.mediaPlayer.demo.util.JwtAccessDeniedHandler;
import com.example.mediaPlayer.demo.util.JwtAuthenticationEntryPoint;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // ❌ Disable CSRF (JWT)
                .csrf(csrf -> csrf.disable())

                // 🔐 Stateless Session
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 🚨 Exception Handling
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                )

                // 🔑 AUTHORIZATION RULES (ONLY ONCE!)
                .authorizeHttpRequests(auth -> auth

                        // PUBLIC
                        .requestMatchers("/authController/**", "/ws/**").permitAll()

                        // ADMIN ONLY
                        .requestMatchers(
                                "/songs/uploadSong/**",
                                "/songs/deleteSong/**",
                                "/albums/createAlbum",
                                "/albums/deleteAlbum",
                                "/artists/createArtist",
                                "/artists/deleteArtistById",
                                "/genres/createGenre",
                                "/genres/delete"
                        ).hasRole("ADMIN")

                        // USER + ADMIN
                        .requestMatchers(
                                "/songs/**",
                                "/albums/getAlbums",
                                "/albums/getAlbumById/",
                                "/artists/**",
                                "/genres/getAllGenres",
                                "/genres/getGenreById/**",
                                "/genres/recommendByGenre/**",
                                "/recommendations/**",
                                "/playlists/**"
                        ).permitAll()

                        // USER ONLY
                        .requestMatchers(
                                "/likes/likeSong/**",
                                "/likes/getLikedSongs",
                                "/likes/unlike/**",
                                "/history/**"
                        ).hasRole("USER")

                        .anyRequest().authenticated()
                )

                // 🔎 JWT FILTER (ONLY ONCE)
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
