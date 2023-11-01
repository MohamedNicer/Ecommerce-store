package com.ecs.ecommercestore.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Address for the user to be billed/delivered to.
 * @author mohamednicer
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
public class Address {

    /** Unique id for the address. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    /** The first line of address. */
    @Column(name = "address_line_1", nullable = false, length = 512)
    private String addressLine1;

    /** The second line of address. */
    @Column(name = "address_line_2", length = 512)
    private String addressLine2;

    /** The city of the address. */
    @Column(name = "city", nullable = false)
    private String city;

    /** The country of the address. */
    @Column(name = "country", nullable = false, length = 75)
    private String country;

    /** The user the address is associated with. */
    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private LocalUser user;
}
