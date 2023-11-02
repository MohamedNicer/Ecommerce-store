package com.ecs.ecommercestore.Repository;

import com.ecs.ecommercestore.Entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data Access Object or Repository for the Address data.
 * @author mohamednicer
 */
@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    List<Address> findByUser_Id(Long id);

}
