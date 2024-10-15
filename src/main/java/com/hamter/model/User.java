package com.hamter.model;

import javax.persistence.*;

import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "Users") 
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false) 
    private String password;

    private String firstName;
    private String lastName;
    private String address;

    private Boolean gender; 

    @Column(name = "roleId") 
    private String roleId;

    @Column(name = "phoneNumber") 
    private String phoneNumber;

    @Column(name = "positionId")
    private String positionId;

    private String image;

    @Temporal(TemporalType.TIMESTAMP) 
    @Column(name = "createdAt", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP) 
    @Column(name = "updatedAt", nullable = false)
    private Date updatedAt;

}
