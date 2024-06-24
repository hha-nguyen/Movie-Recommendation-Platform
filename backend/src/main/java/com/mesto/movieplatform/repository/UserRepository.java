package com.mesto.movieplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.apigateway.model.Op;
import com.mesto.movieplatform.entities.User;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserById(Integer id);

    Optional<User> findByUsername(String name);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<User> findUserByEmail(String email);

    Optional<Boolean> existsByPassword(String password);

    boolean existsById(String user_id);

    Optional<Boolean> existsByEmailAndPassword(String email, String password);

    Optional<User> findUserByEmailAndPassword(String email, String password);
}
