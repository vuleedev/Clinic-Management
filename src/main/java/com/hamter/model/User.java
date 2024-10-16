package com.hamter.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "Users") 
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;
    
    @Size(min = 6, max = 20, message = "Mật khẩu phải từ 6 đến 20 ký tự")
    @Column(nullable = false) 
    private String password;
    
    @NotBlank(message = "Không được để trống")
    private String firstName;
    
    @NotBlank(message = "Không được để trống")
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
