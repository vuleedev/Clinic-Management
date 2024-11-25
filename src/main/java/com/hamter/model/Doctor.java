package com.hamter.model;

import javax.persistence.*;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "doctors")
public class Doctor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(columnDefinition = "nvarchar(255)")
    private String name;

    private String email;

    private String phoneNumber;
    
    private Boolean gender;

    @Lob
    private String profilePicture;

    @ManyToOne
    @JoinColumn(name = "specialty_id", nullable = false)
    private Specialty specialty;
    
    @ManyToOne
    @JoinColumn(name = "clinic_id", nullable = false)
    private Clinic clinic;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; 

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedAt", nullable = false)
    private Date updatedAt;
}
