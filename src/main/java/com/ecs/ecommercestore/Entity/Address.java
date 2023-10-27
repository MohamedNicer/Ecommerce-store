package com.ecs.ecommercestore.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;
    @Column(name = "address_line_1",nullable = false,length = 512)
    private String addressLine1;
    @Column(name = "address_line_2",length = 512)
    private String addressLine2;
    @Column(name = "city",nullable = false)
    private String city;
    @Column(name = "country",nullable = false,length = 60)
    private String country;
    @ManyToOne (optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private LocalUser user;
}
