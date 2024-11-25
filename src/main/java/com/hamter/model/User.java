package com.hamter.model;

import javax.persistence.*;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Users") 
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false) 
    private String password;
    
    @Column(columnDefinition = "nvarchar(50)")
    private String UserName;
    
    @Column(columnDefinition = "nvarchar(100)")
    private String address;

    private Boolean gender; 

    @Column(name = "phoneNumber") 
    private String phoneNumber;

    private String image;

    @Temporal(TemporalType.TIMESTAMP) 
    @Column(name = "createdAt", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP) 
    @Column(name = "updatedAt", nullable = false)
    private Date updatedAt;
    
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Authority> authorities;
}
