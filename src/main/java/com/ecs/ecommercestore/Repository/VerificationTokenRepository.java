package com.ecs.ecommercestore.Repository;

import com.ecs.ecommercestore.Entity.LocalUser;
import com.ecs.ecommercestore.Entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object or Repository for the VerificationToken data.
 * @author mohamednicer
 */
@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
    Optional<VerificationToken> findByToken(String token);
    void deleteAllByUser(LocalUser user);
    List<VerificationToken> findByUser_IdOrderByIdDesc(Long id);
}
