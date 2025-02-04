package io.seb.userservice.repositories;

import io.seb.userservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Token save(Token token);

    Optional<Token> findByValueAndDeleted(String value, boolean isDeleted);

    Optional<Token> findByValueAndDeletedAndExpiryAtGreaterThanEqual(String value, boolean isDeleted, Date expiryAt);
}
