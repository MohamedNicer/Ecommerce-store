package com.ecs.ecommercestore.Repository;

import com.ecs.ecommercestore.Entity.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocalUserRepository extends JpaRepository<LocalUser,Long> {
    Optional<LocalUser> findUserByUsernameIgnoreCase(String username);
    Optional<LocalUser> findUserByEmailIgnoreCase(String email);
}
