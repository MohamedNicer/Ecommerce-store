package com.ecs.ecommercestore.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "verification_token")
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;
    @Lob
    @Column(name = "token",nullable = false,unique = true)
    private String token;
    @Column(name = "created_timestamp",nullable = false)
    private Timestamp createdTimestamp;
    @ManyToOne(optional=false)
    @JoinColumn (name="user_id", nullable = false)
    private LocalUser user;
}
