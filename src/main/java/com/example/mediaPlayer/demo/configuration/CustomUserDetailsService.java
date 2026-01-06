package com.example.mediaPlayer.demo.configuration;
import com.example.mediaPlayer.demo.Entity.User;
import com.example.mediaPlayer.demo.Repo.UserRepo;
import com.example.mediaPlayer.demo.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new CustomUserDetails(
                user.getId(),                // ✅ include userId
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }
}


