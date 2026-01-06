package com.example.mediaPlayer.demo.Repo;

import com.example.mediaPlayer.demo.Entity.User;
import com.example.mediaPlayer.demo.util.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User ,Long> {


   //@Query(value = "select * from users where  username =:username " , nativeQuery = true)
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<Object> findByEmail(String email);

    boolean existsByRole(Role admin);
}
