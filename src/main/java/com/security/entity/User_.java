package com.security.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User_ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private  String email;
    private String password;
    private String role;
}
