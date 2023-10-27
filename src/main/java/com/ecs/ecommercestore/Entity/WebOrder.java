package com.ecs.ecommercestore.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "web_order")
public class WebOrder {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;
    @ManyToOne (optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    private LocalUser user;
    @ManyToOne (optional = false)
    @JoinColumn(name="address id",nullable = false)
    private Address address;
    @OneToMany (mappedBy = "order",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    private List<WebOrderQuantity> quantities = new ArrayList<>();

}
