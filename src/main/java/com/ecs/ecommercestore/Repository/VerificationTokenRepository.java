package com.ecs.ecommercestore.Repository;

import com.ecs.ecommercestore.Entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {

}
