package com.techzen.techlearn.repository;

import com.techzen.techlearn.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("SELECT t FROM Token t INNER JOIN UserEntity u ON t.user.id = u.id WHERE u.id = :userId AND (t.expired = false OR t.revoked = false)")
    List<Token> findAllValidTokenByUser(UUID userId);

    Optional<Token> findByToken(String token);
}