package com.example.user.repository;

import com.example.user.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TokenRepository extends
        JpaRepository<Token,Long> {
    Optional<Token> findByValueAndDeletedEquals(String token, boolean isDeleted);

    Optional<Token> findByValueAndDeletedEqualsAndExpireAtGreaterThan(String token, boolean isDeleted, Date date);
}
