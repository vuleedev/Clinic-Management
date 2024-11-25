package com.hamter.model;

import javax.persistence.*;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "clinics")
public class Clinic implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(columnDefinition = "nvarchar(100)")
    private String name;
    
    @Column(columnDefinition = "nvarchar(100)")
    private String address;

    @Column(columnDefinition = "nvarchar(max)")
    private String description;

    private String image;
    
    @OneToMany(mappedBy = "clinic")
    private List<Doctor> doctors;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedAt", nullable = false)
    private Date updatedAt;
 
}