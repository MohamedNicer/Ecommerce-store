package com.ecs.ecommercestore.Repository;

import com.ecs.ecommercestore.Entity.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocalUserRepository extends JpaRepository<LocalUser,Long> {
    Optional<LocalUser> findLocalUserByUsernameIgnoreCase(String username);
    Optional<LocalUser> findLocalUserByEmailIgnoreCase(String email);
}
