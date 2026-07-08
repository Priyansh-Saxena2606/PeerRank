package com.peerrank.peerrank.repository;

import com.peerrank.peerrank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.peerrank.peerrank.repository.UserRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    long count();


}