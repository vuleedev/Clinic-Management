package com.hamter.model;

import javax.persistence.*;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "clinics")
public class Clinic implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(columnDefinition = "nvarchar(255)")
    private String name;
    
    @Column(columnDefinition = "nvarchar(255)")
    private String address;

    @Lob
    @Column(columnDefinition = "nvarchar(255)")
    private String description;

    private String image;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedAt", nullable = false)
    private Date updatedAt;
 
}