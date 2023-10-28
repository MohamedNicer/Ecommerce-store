package com.ecs.ecommercestore.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "local_user")
public class LocalUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Getter @Setter private Long id;
    @Column(name = "username",nullable = false,unique = true)
    @Getter @Setter private String username;
    @Column(name = "password",nullable = false,length = 1000)
    @JsonIgnore
    @Getter @Setter private String password;
    @Column(name = "email",nullable = false,unique = true,length = 320)
    @Getter @Setter private String email;
    @Column(name = "first_name",nullable = false)
    @Getter @Setter private String firstName;
    @Column(name = "last_name",nullable = false)
    @Getter @Setter private String lastName;
    @OneToMany(mappedBy = "user", cascade = CascadeType. REMOVE, orphanRemoval = true)
    @JsonIgnore
    @Getter @Setter private List<Address> addresses = new ArrayList<>();
}
