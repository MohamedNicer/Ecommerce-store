package com.ecs.ecommercestore.Repository;

import com.ecs.ecommercestore.Entity.LocalUser;
import com.ecs.ecommercestore.Entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
    Optional<VerificationToken> findByToken(String token);
    void deleteAllByUser(LocalUser user);
}
